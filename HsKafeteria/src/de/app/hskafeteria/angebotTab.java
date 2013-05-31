package de.app.hskafeteria;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.Angebot;
import de.app.hskafeteria.httpclient.domain.AngeboteList;
import de.app.hskafeteria.httpclient.domain.Kategorie;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
	 
public class angebotTab extends Fragment {
    
    private LinkedHashMap<String, Kategorie> kategorien = new LinkedHashMap<String, Kategorie>();
    private ArrayList<Kategorie> katList = new ArrayList<Kategorie>();
    private AngeboteListAdapter listAdapter;
    private ExpandableListView expandableList;
    private Context ctx;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    kategorien = new LinkedHashMap<String, Kategorie>();
	    katList = new ArrayList<Kategorie>();
		
		ctx = this.getActivity();
		
		View inflatedView = inflater
				.inflate(R.layout.angebot, container, false);

		expandableList = (ExpandableListView) inflatedView.findViewById(R.id.expandable_list_angebot);

		new AngebotAsyncTask(this.getActivity()).execute();

		// listener for child row click
		expandableList.setOnChildClickListener(myListItemClicked);
		
//		// listener for group heading click
//		expandableList.setOnGroupClickListener(myListGroupClicked);
		

		return inflatedView;
    }
	
	 //our child listener
	 private OnChildClickListener myListItemClicked =  new OnChildClickListener() {
	 
	  public boolean onChildClick(ExpandableListView parent, View v,
	    int groupPosition, int childPosition, long id) {
	    
	   //get the group header
	   Kategorie kategorie = katList.get(groupPosition);
	   
	   //get the child info
	   Angebot angebot =  kategorie.getAngebote().get(childPosition);
	   
	   Bundle korb = new Bundle();
	   korb.putString("angebotTitel", angebot.getTitel());
	   korb.putParcelable("angebot", angebot);
	   
	   Intent in = new Intent(ctx, AngebotDetails.class);
	   in.putExtras(korb);
	   
	   startActivity(in);
	   
	   return false;
	  }
	   
	 };
	 
//	 //our group listener
//	 private OnGroupClickListener myListGroupClicked =  new OnGroupClickListener() {
//	 
//	  public boolean onGroupClick(ExpandableListView parent, View v,
//	    int groupPosition, long id) {
//	    
//	   //get the group header
//	   Kategorie headerInfo = katList.get(groupPosition);
//	   //display it or do something with it
//	   Toast.makeText(getActivity(), "Child on Header " + headerInfo.getTitel(),
//	     Toast.LENGTH_LONG).show();
//	     
//	   return false;
//	  }
//	   
//	 };
	
	private class AngebotAsyncTask extends AsyncTask<Void, Void, List<Angebot>> {
		
		private ProgressDialog pDlg = null;
		private Context mContext = null;
		private String processMessage = "Angebote werden geladen...";
		
        public AngebotAsyncTask(Context mContext) {
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
		protected List<Angebot> doInBackground(Void... params) {
			NetClient netClient = new NetClient();
			AngeboteList angeboteList = netClient.getAllAngebote();
			if (angeboteList != null)
				return angeboteList.getAngeboteList();
			else
				return null;
		}

		@Override
		protected void onPostExecute(List<Angebot> result) {
			super.onPostExecute(result);
			if ((result == null) || (result.size() == 0)) {
				Toast.makeText(getActivity(), "Die Angebote konnten nicht geladen werden.", Toast.LENGTH_LONG).show();
			}
			else {

				for (int z = 0; z < result.size(); z++) {

					// check the hash map if the group already exists
					Kategorie kategorie = kategorien.get(result.get(z).getArt());
					
					// add the group if it doesn't exists
					if (kategorie == null) {
						kategorie = new Kategorie(result.get(z).getArt());
						kategorien.put(result.get(z).getArt(), kategorie);
						katList.add(kategorie);
					}

					// get the children for the group
					ArrayList<Angebot> angebotList = kategorie.getAngebote();
				

					// create a new child and add that to the group
					angebotList.add(result.get(z));
					kategorie.setAngebote(angebotList);
				}

				// create the adapter by passing your ArrayList data
				listAdapter = new AngeboteListAdapter(angebotTab.this.getActivity(), katList);
				
				// attach the adapter to the list
				expandableList.setAdapter(listAdapter);
			}
			pDlg.dismiss();
		}
	}

}