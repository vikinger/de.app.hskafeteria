package de.app.hskafeteria;


import java.io.InputStream;
import java.util.Date;
import java.util.List;

import de.app.hskafeteria.datetime.DateTimeFormatter;
import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.Angebot;
import de.app.hskafeteria.httpclient.domain.Benutzer;
import de.app.hskafeteria.httpclient.domain.Bewertung;
import de.app.hskafeteria.httpclient.domain.Bewertungen;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class AngebotDetails extends Activity {

	private ListView listview;
	private RatingBar ratingBar;
	private Button bewertenButton;
	private Angebot angebot;
	private Integer preis;
	private Double preisD;
	private String preisStr;
	private EditText kommentar;
	View popupView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		angebot = bundle.getParcelable("angebot");
		preis = bundle.getInt("angebotPreis");
		angebot.setPreis(preis);
		
		preisD = (double)preis;
		preisD = preisD/100;
		
		preisStr = String.valueOf(preisD) + "0";
		
		setContentView(new AngebotUI(this, angebot));

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		listview = (ListView) findViewById(R.id.bwList);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageViewAngebot);

		String angebotTitel = angebot.getTitel();

		new AngebotDetailsAsyncTask(this).execute(angebotTitel);
		
		String imgurl = "http://hskafeteria.square7.ch/"+angebotTitel+".jpg";
		
		if (angebotTitel.contains(" ") || angebotTitel.contains("ü") || angebotTitel.contains("ö") || angebotTitel.contains("ä") || angebotTitel.contains("ß"))
		{
			String newTitle = "";
			
			newTitle = angebotTitel.replaceAll("\\s", "%20");
			newTitle = angebotTitel.replaceAll("ü", "%C3%BC");
			newTitle = angebotTitel.replaceAll("ö", "%C3%B6");
			newTitle = angebotTitel.replaceAll("ä", "%C3%A4");
			
			imgurl = "http://hskafeteria.square7.ch/"+newTitle+".jpg";
		}

//		String imgurl = "http://www.iwi.hs-karlsruhe.de/ebaws/~eb12/eB-Icon1.png";
		
		new DownloadImageTask(imageView).execute(imgurl);

		bewertenButton = (Button) findViewById(R.id.bw_button);
		bewertenButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getBaseContext());
				String loggedInUser = prefs.getString("logged_in_user", "");
				if (loggedInUser.isEmpty() || loggedInUser == null) {
					showConfirmationDialog();
				}

				LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				popupView = layoutInflater
						.inflate(R.layout.bw_popup, null);
				final PopupWindow popupWindow = new PopupWindow(popupView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				kommentar = (EditText) popupView.findViewById(R.id.editText1);

				Button btnDismiss = (Button) popupView
						.findViewById(R.id.dismiss);
				btnDismiss.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						popupWindow.dismiss();
					}
				});

				Button btnInsert = (Button) popupView
						.findViewById(R.id.button1);
				btnInsert.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						if(kommentar.getText().toString().isEmpty())
						{
							Toast.makeText(getBaseContext(), "Es muss ein Kommentar eingegeben werden!",
									Toast.LENGTH_SHORT).show();
							return;
						}
						bewertungAnlegen();
						finish();
						startActivity(getIntent());
					}
				});

				popupWindow.setFocusable(true);
				popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
			}
		});
	}

	private void showConfirmationDialog() {
		final Intent login = new Intent(this, Login.class);
		final Intent cancel = new Intent(this, MainActivity.class);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set title
		alertDialogBuilder
				.setTitle("Zum Bewerten eines Artikels bitte einloggen!");
		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Login",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
								login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(login);
							}
						})
				.setNegativeButton("Abbrechen",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
								cancel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								finish();
								startActivity(cancel);
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void bewertungAnlegen() {
		Bewertung bewertung = new Bewertung();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String benutzerEmail = prefs.getString("logged_in_user", "");
		Benutzer benutzer = new Benutzer();
		benutzer.setEmail(benutzerEmail);

		bewertung.setBenutzer(benutzer);
		bewertung.setAngebot(angebot);
		bewertung.setPunkte((int) ((RatingBar) popupView.findViewById(R.id.ratingBarNeueBewertung))
				.getRating());

		String textkommentar = kommentar.getText().toString();

		bewertung.setKommentar(textkommentar);

		DateTimeFormatter formatter = new DateTimeFormatter(new Date());
		String dateStr = formatter.getDate();
		String timeStr = formatter.getTime();

		formatter = new DateTimeFormatter(dateStr, timeStr);

		bewertung.setDatum(formatter.getDateInLong());

		if ((bewertung.getPunkte() < 1))
			{
			Toast.makeText(getBaseContext(), "Bewertung muss mindestens einen Stern haben!",
					Toast.LENGTH_SHORT).show();
		} 
		else if(bewertung.getKommentar().isEmpty()) 
		{
			Toast.makeText(getBaseContext(), "Die Eingabe war nicht korrekt",
					Toast.LENGTH_SHORT).show();
		}
		else {
			new BewertenAsyncTask().execute(bewertung);
		}
	}

	class AngebotUI extends LinearLayout {
		private Angebot angebot = null;

		public AngebotUI(Context context, Angebot angebot) {
			super(context);
			this.angebot = angebot;
			inflateLayout(context);
		}

		public AngebotUI(Context context, AttributeSet attrs) {
			super(context, attrs);
			inflateLayout(context);
		}

		@SuppressLint("SimpleDateFormat")
		private void inflateLayout(Context context) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(
					R.layout.activity_angebot_details, this);
			
			String angebotTitel = angebot.getTitel();
			
			if (angebotTitel.contains("ue") || angebotTitel.contains("oe") || angebotTitel.contains("ae") || angebotTitel.contains("ss"))
			{
				  angebotTitel = angebotTitel.replaceAll("ue", "ü");
				  angebotTitel = angebotTitel.replaceAll("oe", "ö");
				  angebotTitel = angebotTitel.replaceAll("ae", "ä");
				  angebotTitel = angebotTitel.replaceAll("ss", "ß");
			}
			
			((TextView) view.findViewById(R.id.angebotTitel)).setText(angebotTitel);
			((TextView) view.findViewById(R.id.angebotZutaten)).setText(angebot
					.getZutaten());
			((TextView) view.findViewById(R.id.angebotPreis)).setText(preisStr + "€");

			ratingBar = (RatingBar) findViewById(R.id.ratingBarGesamt);
		}
	}

	private class AngebotDetailsAsyncTask extends
			AsyncTask<String, Void, List<Bewertung>> {

		private ProgressDialog pDlg = null;
		private Context mContext = null;
		private String processMessage = "Bewertungen werden geladen...";

		public AngebotDetailsAsyncTask(Context mContext) {
			this.mContext = mContext;
		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

		@Override
		protected void onPreExecute() {

			showProgressDialog();

		}

		@Override
		protected List<Bewertung> doInBackground(String... params) {
			NetClient netClient = new NetClient();
			String angebotTitel = params[0];
			if (angebotTitel.contains(" ")) {
				angebotTitel = angebotTitel.replaceAll("\\s", "%20");
			}

			Bewertungen bewertungen = netClient
					.getBewertungenByAngebot(angebotTitel);
			if (bewertungen != null){
				return bewertungen.getBewertungen();
			}
			else{
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<Bewertung> result) {
			super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(getBaseContext(),
						"Die Bewertungen konnten nicht geladen werden.",
						Toast.LENGTH_LONG).show();
			} else if(result.size() == 0){
				Toast.makeText(getBaseContext(),
						"Es wurden noch keine Bewertungen zu diesem Artikel abgegeben.",
						Toast.LENGTH_LONG).show();
			}
			else
			{

				float punkte = 0;

				for (int z = 0; z < result.size(); z++) 
				{
					punkte = punkte + result.get(z).getPunkte();
				}

				ratingBar.setRating(punkte / result.size());

				BewertungenListAdapter adapter = new BewertungenListAdapter(getBaseContext(), result);
				listview.setAdapter(adapter);

			}
			pDlg.dismiss();
		}
	}

	private class BewertenAsyncTask extends AsyncTask<Bewertung, Void, Integer> {

		@Override
		protected Integer doInBackground(Bewertung... params) {
			NetClient netClient = new NetClient();
			return netClient.createBewertung(params[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 201) {
				Toast.makeText(getBaseContext(), "Bewertung eingetragen",
						Toast.LENGTH_LONG);
			} else {
				Toast.makeText(
						getBaseContext(),
						"Bewertung konnte nicht eingetragen werden. Code = "
								+ result, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
}
