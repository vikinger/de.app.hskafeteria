package de.app.hskafeteria;

import java.util.ArrayList;
import java.util.List;

import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.Aktion;
import de.app.hskafeteria.httpclient.domain.AktionenList;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
	 
public class aktionenTab extends Fragment {
    
    private ExpandableListView mExpandableList;

	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View inflatedView = inflater.inflate(R.layout.aktionen, container, false);

        mExpandableList = (ExpandableListView) inflatedView.findViewById(R.id.expandable_list_aktionen);
        
        new AktionenAsyncTask().execute();
        
        return inflatedView;
    }
	
	private class AktionenAsyncTask extends AsyncTask<Void, Void, List<Aktion>> {
		
		@Override
		protected List<Aktion> doInBackground(Void... params) {
			NetClient netClient = new NetClient();
			AktionenList aktionenList = netClient.getAllAktionen();
			if (aktionenList != null)
				return aktionenList.getAktionenList();
			else
				return null;
		}

		@Override
		protected void onPostExecute(List<Aktion> result) {
			super.onPostExecute(result);
			if ((result == null) || (result.size() == 0)) {
				Toast.makeText(getActivity(), "Keine Aktionen vorhanden", Toast.LENGTH_SHORT).show();
			}
			else {
		        ArrayList<Aktion> arrayParents = new ArrayList<Aktion>();
		    	
		        for (int z = 0; z < result.size(); z++){
		        	
		        	Aktion aktion = result.get(z);
		            
		            arrayParents.add(aktion);
		           
		        }
				mExpandableList.setAdapter(new AktionenListAdapter(aktionenTab.this.getActivity(), arrayParents));
			}
		}
	}

}