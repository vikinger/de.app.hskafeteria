package de.app.hskafeteria;

import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.Benutzer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	private Context ctx;
	private String textEmail = null;
	private String nameOfUser = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = getBaseContext();
		setContentView(R.layout.activity_login);
		
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		final Button anmeldeButton = (Button) findViewById(R.id.bt_login_anmelden);
		final Button goToRegistrationActivity = (Button) findViewById(R.id.bt_login_registrieren);


		anmeldeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				anmelden();
			}
		});

		goToRegistrationActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ctx, Registrierung.class));
			}
		});
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

	protected void anmelden() {
		EditText email = (EditText)findViewById(R.id.tx_login_email);
		textEmail = email.getText().toString();

		EditText passwort = (EditText)findViewById(R.id.tx_login_passwort);
		String textPassword = passwort.getText().toString();

		if (textEmail.equals("") || textPassword.equals("")) {
			Toast.makeText(this, "Bitte alle Felder ausfüllen",
					Toast.LENGTH_LONG).show();
			return;
		}
//		new GetUserNameAsyncTask().execute(textEmail);

		
		new LoginAsyncTask().execute(textEmail, textPassword);
	}

	private class LoginAsyncTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			String username = params[0];
			String password = params[1];
			NetClient netClient = new NetClient();
					
			return netClient.login(username, password);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 200) {
			
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
				prefs.edit().putString("logged_in_user", textEmail).commit();
				
				prefs.edit().putString("logged_in_user_name", nameOfUser).commit();
				startActivity(new Intent(ctx, MainActivity.class));
			}
			else {
				Toast.makeText(Login.this, "Email/Passwort falsch", Toast.LENGTH_LONG).show();
			}
		}

	}
	
//	private class GetUserNameAsyncTask extends AsyncTask<String, Void, Benutzer> {
//		
//		@Override
//		protected Benutzer doInBackground(String... params) {
//			String email = params[0];
//			
//			NetClient netClient = new NetClient();
//			return netClient.getBenutzerByEmail(email);
//
//		}
//		
//		@Override
//		protected void onPostExecute(Benutzer result) {
//			super.onPostExecute(result);
//			if (result != null) {
//				nameOfUser = result.getVorname().toString() + " " + result.getNachname().toString();
//				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
//				prefs.edit().putString("logged_in_user_name", nameOfUser).commit();
//			}
//			else {
//				Toast.makeText(Login.this, "Email/Passwort falsch", Toast.LENGTH_LONG).show();
//			}
//		}
//	}
}


