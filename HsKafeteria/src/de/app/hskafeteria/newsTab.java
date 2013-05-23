package de.app.hskafeteria;

import java.util.ArrayList;
import java.util.List;

import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.News;
import de.app.hskafeteria.httpclient.domain.NewsList;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
	 
public class newsTab extends Fragment {
    
    private ExpandableListView mExpandableList;

	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View inflatedView = inflater.inflate(R.layout.news, container, false);

        mExpandableList = (ExpandableListView) inflatedView.findViewById(R.id.expandable_list_news);
        
        new NewsAsyncTask().execute();
        
        return inflatedView;
    }
	
	private class NewsAsyncTask extends AsyncTask<Void, Void, List<News>> {
		
		@Override
		protected List<News> doInBackground(Void... params) {
			NetClient netClient = new NetClient();
			NewsList newsList = netClient.getAllNews();
			if (newsList != null)
				return newsList.getNewsList();
			else
				return null;
		}

		@Override
		protected void onPostExecute(List<News> result) {
			super.onPostExecute(result);
			if ((result == null) || (result.size() == 0)) {
				Toast.makeText(getActivity(), "Keine News vorhanden", Toast.LENGTH_SHORT).show();
			}
			else {
		        ArrayList<News> arrayParents = new ArrayList<News>();
		    	
		        for (int z = 0; z < result.size(); z++){
		        	
		            News news = result.get(z);
		            News parent = new News();
		            
		            String titel = news.getTitel();
		            String inhalt = news.getInhalt();
		            
		            parent.setTitel(titel);
		            parent.setInhalt(inhalt);
		            
		            arrayParents.add(parent);
		           
		        }
				mExpandableList.setAdapter(new NewsListAdapter(newsTab.this.getActivity(), arrayParents));
			}
		}
	}

}