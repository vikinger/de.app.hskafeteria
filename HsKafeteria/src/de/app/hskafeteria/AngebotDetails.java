package de.app.hskafeteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
	private EditText kommentar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		angebot = bundle.getParcelable("angebot");
		setContentView(new AngebotUI(this, angebot));

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		listview = (ListView) findViewById(R.id.bwList);

		String angebotTitel = angebot.getTitel();

		new AngebotDetailsAsyncTask(this).execute(angebotTitel);

		// SharedPreferences prefs =
		// PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		// String benutzerEmail = prefs.getString("logged_in_user", "");
		// new GetBenutzerAsyncTask().execute(benutzerEmail);

		// SharedPreferences prefs =
		// PreferenceManager.getDefaultSharedPreferences(this);
		// benutzerId = prefs.getString("logged_in_user", "");

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
				View popupView = layoutInflater
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
		bewertung.setPunkte((int) ((RatingBar) findViewById(R.id.ratingBar1))
				.getRating());

		String textkommentar = kommentar.getText().toString();

		bewertung.setKommentar(textkommentar);

		DateTimeFormatter formatter = new DateTimeFormatter(new Date());
		String dateStr = formatter.getDate();
		String timeStr = formatter.getTime();

		formatter = new DateTimeFormatter(dateStr, timeStr);

		bewertung.setDatum(formatter.getDateInLong());

		if (!bewertung.isInputValid()) {
			Toast.makeText(getBaseContext(), "Die Eingabe war nicht korrekt",
					Toast.LENGTH_SHORT).show();
		} else {
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
			((TextView) view.findViewById(R.id.angebotTitel)).setText(angebot
					.getTitel());
			((TextView) view.findViewById(R.id.angebotZutaten)).setText(angebot
					.getZutaten());
			((TextView) view.findViewById(R.id.angebotPreis)).setText(Double
					.toString(angebot.getPreis()));

			ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		}
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
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
			if (bewertungen != null)
				return bewertungen.getBewertungen();
			else
				return null;
		}

		@Override
		protected void onPostExecute(List<Bewertung> result) {
			super.onPostExecute(result);
			if ((result == null) || (result.size() == 0)) {
				Toast.makeText(getBaseContext(),
						"Die Bewertungen konnten nicht geladen werden.",
						Toast.LENGTH_LONG).show();
			} else {

				ArrayList<String> list = new ArrayList<String>();
				float punkte = 0;

				for (int z = 0; z < result.size(); z++) {

					list.add(result.get(z).getKommentar());
					punkte = punkte + result.get(z).getPunkte();
				}

				ratingBar.setRating(punkte / result.size());

				StableArrayAdapter adapter = new StableArrayAdapter(
						getBaseContext(), android.R.layout.simple_list_item_1,
						list);
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

	// private class GetBenutzerAsyncTask extends AsyncTask<String, Void,
	// Benutzer> {
	//
	// @Override
	// protected Benutzer doInBackground(String... params) {
	// String email = params[0];
	//
	// NetClient netClient = new NetClient();
	// return netClient.getBenutzerByEmail(email);
	//
	// }
	//
	// @Override
	// protected void onPostExecute(Benutzer result) {
	// super.onPostExecute(result);
	// if (result != null) {
	// benutzer = result;
	// }
	// else {
	// Toast.makeText(getBaseContext(),
	// "Der Benutzer konnte nicht ausgelesen werden", Toast.LENGTH_LONG).show();
	// }
	// }
	// }
}