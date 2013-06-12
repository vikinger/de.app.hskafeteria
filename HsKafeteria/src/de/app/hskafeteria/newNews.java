package de.app.hskafeteria;


import java.util.Date;

import de.app.hskafeteria.datetime.DateTimeFormatter;
import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.News;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class newNews extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newnews);
		
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		final Button insertButton = (Button) findViewById(R.id.bt_newnews_insert);
		insertButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				newsEintragen();
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	super.onBackPressed();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	protected void newsEintragen()
	{
		EditText titel = (EditText)findViewById(R.id.tx_newnews_titel);
		String texttitel = titel.getText().toString();

		EditText inhalt = (EditText)findViewById(R.id.tx_newnews_inhalt);
		String textinhalt = inhalt.getText().toString();

		News neueNews = new News();
		neueNews.setTitel(texttitel);
		neueNews.setInhalt(textinhalt);
		
		DateTimeFormatter formatter = new DateTimeFormatter(new Date());
		String dateStr = formatter.getDate();
		String timeStr = formatter.getTime();
		
		formatter = new DateTimeFormatter(dateStr, timeStr);

		neueNews.setDatum(formatter.getDateInLong());
		


		if (!neueNews.isInputValid()) {
			Toast.makeText(this, "Bitte alle Felder ausfüllen", Toast.LENGTH_LONG).show();
			return;
		}

		new NewNewsAsyncTask().execute(neueNews);
	}

	private class NewNewsAsyncTask extends AsyncTask<News, Void, Integer> {

		@Override
		protected Integer doInBackground(News... params) {
			NetClient netClient = new NetClient();
			return netClient.createNews(params[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 201) {
				Toast.makeText(newNews.this.getApplicationContext(), "Die News wurde erfolgreich eingetragen", Toast.LENGTH_LONG);
				startActivity(new Intent(newNews.this, MainActivity.class));
			}
			else {
				Toast.makeText(newNews.this, "Die News konnte nicht eingetragen werden. Code = " + result, Toast.LENGTH_LONG).show();
			}
		}
	}
}
