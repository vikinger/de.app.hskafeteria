package de.app.hskafeteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.News;
import de.app.hskafeteria.httpclient.domain.NewsList;
import de.app.hskafeteria.datetime.DateTimeFormatter;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
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
        
        new NewsAsyncTask(this.getActivity()).execute();
        
        return inflatedView;
    }
	
	private class NewsAsyncTask extends AsyncTask<Void, Void, List<News>> {
		
		private ProgressDialog pDlg = null;
		private Context mContext = null;
		private String processMessage = "News werden geladen...";
		
        public NewsAsyncTask(Context mContext) {
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
				Toast.makeText(getActivity(), "Die News konnten nicht geladen werden.", Toast.LENGTH_LONG).show();
			}
			else {
		        ArrayList<News> arrayParents = new ArrayList<News>();
		    	
		        for (int z = 0; z < result.size(); z++){
		        	
		            News news = result.get(z);
		            
		            DateTimeFormatter formatter = new DateTimeFormatter(new Date(news.getDatum()));
		            formatter.getDate();
		            
		            
		            arrayParents.add(news);
		           
		        }
				mExpandableList.setAdapter(new NewsListAdapter(newsTab.this.getActivity(), arrayParents));
			}
			pDlg.dismiss();
		}
	}

}