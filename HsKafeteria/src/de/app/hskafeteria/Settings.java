package de.app.hskafeteria;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import de.app.hskafeteria.httpclient.client.NetClient;

public class Settings extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	Context ctx = null;
	
	private String newPassword;
	private String email;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		addPreferencesFromResource(R.xml.prefs);

		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		final String name = prefs.getString("Test", "");
		final String email = prefs.getString("logged_in_user", "");

		Preference loggedInUserPref = findPreference("logged_in_user_name");
		loggedInUserPref.setSummary(name);

		loggedInUserPref = findPreference("logged_in_user");
		loggedInUserPref.setSummary(email);
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

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("logged_in_password")) {
			final String newPassword = sharedPreferences.getString(key, "");
			final String email = sharedPreferences.getString("logged_in_user",
					"");
			(new UpdatePasswordAsyncTask()).execute(email, newPassword);
		}
	}
	
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            final Preference preference) {

     if(preference.getKey().toString().toLowerCase().equals("changepassword")) {
    	 
    	 LayoutInflater factory = LayoutInflater.from(this);            
    	 final View textEntryView = factory.inflate(R.layout.popup_changepassword, null);
    	 AlertDialog.Builder alert = new AlertDialog.Builder(this); 

    	  alert.setTitle("Passwort ändern"); 
    	  // Set an EditText view to get user input  
    	  alert.setView(textEntryView); 
    	  AlertDialog loginPrompt = alert.create();

    	  final EditText old = (EditText) textEntryView.findViewById(R.id.oldPassword);
    	  final EditText new1 = (EditText) textEntryView.findViewById(R.id.newPassword1);
    	  final EditText new2 = (EditText) textEntryView.findViewById(R.id.newPassword2);

    	  alert.setPositiveButton("Passwort speichern", new DialogInterface.OnClickListener() { 
    	  public void onClick(DialogInterface dialog, int whichButton) { 

    		  if(!new1.getText().toString().equals(new2.getText().toString()))
    		  {
  				Toast.makeText(Settings.this, "Die Eingaben der neuen Passwörter unterscheiden sich!",
						Toast.LENGTH_SHORT).show();

    		  }
    		  else{
    			newPassword = new1.getText().toString();
  				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getBaseContext());
				email = prefs.getString("logged_in_user", "");
				
				new LoginAsyncTask().execute(email, old.getText().toString());
    		  }

    	 }}); 

    	  alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() { 
    	  public void onClick(DialogInterface dialog, int whichButton) { 
    	              dialog.cancel();
    	                  } 
    	  }); 

    	  alert.show(); 

    	
     }
     return true;
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
				(new UpdatePasswordAsyncTask()).execute(email, newPassword);
			}
			else {
				Toast.makeText(Settings.this, "Aktuelles Passwort ist falsch!", Toast.LENGTH_LONG).show();
			}
		}

	}

	private class UpdatePasswordAsyncTask extends
			AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			String userEmail = params[0];
			String newPassword = params[1];
			NetClient netClient = new NetClient();
			return netClient.changePassword(userEmail, newPassword);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 201) {
				Toast.makeText(Settings.this, "Passwort geändert",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(Settings.this, "Probleme...", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}
}
