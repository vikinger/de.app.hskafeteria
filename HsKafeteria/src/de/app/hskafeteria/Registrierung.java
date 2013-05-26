package de.app.hskafeteria;


import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.Benutzer;
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


public class Registrierung extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrierung);
		
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		final Button regButton = (Button) findViewById(R.id.bt_reg_registrieren);
		regButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				benutzerAnlegen();
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, Login.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	protected void benutzerAnlegen()
	{
		EditText nachname = (EditText)findViewById(R.id.tx_reg_nachname);
		String textnachname = nachname.getText().toString();

		EditText vorname = (EditText)findViewById(R.id.tx_reg_vorname);
		String textvorname = vorname.getText().toString();

		EditText email = (EditText)findViewById(R.id.tx_reg_email);
		String textemail = email.getText().toString();

		EditText passwort = (EditText)findViewById(R.id.tx_reg_passwort);
		String textpasswort = passwort.getText().toString();

		Benutzer neueBenutzer = new Benutzer();
		neueBenutzer.setEmail(textemail);
		neueBenutzer.setNachname(textnachname);
		neueBenutzer.setVorname(textvorname);
		neueBenutzer.setPasswort(textpasswort);

		if (!neueBenutzer.isInputValid()) {
			Toast.makeText(this, "Bitte alle Felder ausfüllen", Toast.LENGTH_LONG).show();
			return;
		}

		new RegistrationAsyncTask().execute(neueBenutzer);
	}

	private class RegistrationAsyncTask extends AsyncTask<Benutzer, Void, Integer> {

		@Override
		protected Integer doInBackground(Benutzer... params) {
			NetClient netClient = new NetClient();
			return netClient.createNewBenutzer(params[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 201) {
				Toast.makeText(Registrierung.this.getApplicationContext(), "Neue Benutzer erstellt", Toast.LENGTH_LONG);
				startActivity(new Intent(Registrierung.this, Login.class));
			}
			else {
				Toast.makeText(Registrierung.this, "Neue Benutzer konnte nicht erstellt werden :( Code = " + result, Toast.LENGTH_LONG).show();
			}
		}
	}// RegistrationAsyncTask
}
