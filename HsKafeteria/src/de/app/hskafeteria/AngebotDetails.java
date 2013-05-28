package de.app.hskafeteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.Angebot;
import de.app.hskafeteria.httpclient.domain.Bewertung;
import de.app.hskafeteria.httpclient.domain.Bewertungen;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class AngebotDetails extends Activity{

	private ListView listview;
	private RatingBar ratingBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		final Angebot angebot = bundle.getParcelable("angebot");
		setContentView(new AngebotUI(this, angebot));
		
		listview = (ListView) findViewById(R.id.bwList);
		
		String angebotTitel = angebot.getTitel();
		
		new AngebotDetailsAsyncTask(this).execute(angebotTitel);
		
//		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//		benutzerId = prefs.getString("logged_in_user", "");
//		
//		bewertenButton = (Button) findViewById(R.id.btn_bewerten);
//		bewertenButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				
//			}
//		});
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
			LayoutInflater layoutInflater = 
					(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.activity_angebot_details, this);
			((TextView) view.findViewById(R.id.angebotTitel)).setText(angebot.getTitel());
			((TextView) view.findViewById(R.id.angebotZutaten)).setText(angebot.getZutaten());
			((TextView) view.findViewById(R.id.angebotPreis)).setText(Integer.toString(angebot.getPreis()));
			
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
	
	private class AngebotDetailsAsyncTask extends AsyncTask<String, Void, List<Bewertung>> {
		
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
			if(angebotTitel.contains(" "))
			{
				angebotTitel = angebotTitel.replaceAll("\\s", "%20");
			}
			
			Bewertungen bewertungen = netClient.getBewertungenByAngebot(angebotTitel);
			if (bewertungen != null)
				return bewertungen.getBewertungen();
			else
				return null;
		}

		@Override
		protected void onPostExecute(List<Bewertung> result) {
			super.onPostExecute(result);
			if ((result == null) || (result.size() == 0)) {
				Toast.makeText(getBaseContext(), "Die Bewertungen konnten nicht geladen werden.", Toast.LENGTH_LONG).show();
			}
			else {

				ArrayList<String> list = new ArrayList<String>();
				float punkte = 0; 
				
				for (int z = 0; z < result.size(); z++) {

			          list.add(result.get(z).getKommentar());
			          punkte = punkte + result.get(z).getPunkte();
				}
				
				
				ratingBar.setRating(punkte/result.size());
				
		        StableArrayAdapter adapter = new StableArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, list);
				listview.setAdapter(adapter);

			}
			pDlg.dismiss();
		}
	}
}
