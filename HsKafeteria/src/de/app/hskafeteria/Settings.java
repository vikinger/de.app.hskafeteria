package de.app.hskafeteria;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;
import de.app.hskafeteria.httpclient.client.NetClient;

public class Settings extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	Context ctx = null;
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;

		addPreferencesFromResource(R.xml.prefs);

		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		prefs.registerOnSharedPreferenceChangeListener(this);
		final String email = prefs.getString("logged_in_user", "");
		
		Preference loggedInUserPref = findPreference("logged_in_user");
		loggedInUserPref.setSummary(email);
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals("logged_in_password")) {
			final String newPassword = sharedPreferences.getString(key, "");
			final String email = sharedPreferences.getString("logged_in_user", "");
			(new UpdatePasswordAsyncTask()).execute(email, newPassword);
		}
	}
	
	private class UpdatePasswordAsyncTask extends AsyncTask<String, Void, Integer> {

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
				Toast.makeText(Settings.this, "Passwort geändert", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(Settings.this, "Probleme...", Toast.LENGTH_SHORT).show();
			}
		}

	}
}
