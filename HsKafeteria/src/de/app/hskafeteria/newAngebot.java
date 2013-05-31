package de.app.hskafeteria;

import java.util.Calendar;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import de.app.hskafeteria.datetime.DateTimeFormatter;
import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.Angebot;
import de.app.hskafeteria.httpclient.domain.News;

public class newAngebot extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newangebot);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		final Button insertButton = (Button) findViewById(R.id.bt_newangebot_insert);
		insertButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				angebotEintragen();
			}
		});

		final EditText editText = (EditText) findViewById(R.id.tx_newangebot_kategorie);
		editText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final Dialog dialog = new Dialog(newAngebot.this);
				dialog.setContentView(R.layout.popup_kategorie);
				dialog.setTitle(R.string.hint_kategorie_newangebot);
				dialog.setCancelable(true);

				final RadioGroup radioGroup = (RadioGroup) dialog
						.findViewById(R.id.radioGroup);
				radioGroup
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							public void onCheckedChanged(RadioGroup group,
									int checkedId) {
								final EditText et = (EditText) findViewById(R.id.tx_newangebot_kategorie);
								RadioButton radioButton = (RadioButton) radioGroup
										.findViewById(checkedId);
								et.setText(radioButton.getText());
								dialog.dismiss();
							}
						});

				dialog.show();
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

	protected void angebotEintragen() {
		EditText name = (EditText) findViewById(R.id.tx_newangebot_artikelname);
		String artikelname = name.getText().toString();

		EditText kat = (EditText) findViewById(R.id.tx_newangebot_kategorie);
		String kategorie = kat.getText().toString();

		EditText pre = (EditText) findViewById(R.id.tx_newangebot_preis);
		String preis = pre.getText().toString();
		//Validierung des Kommas
		if (preis.contains(",")) {
			if ((preis.length()) - (preis.indexOf(",")) > 3) 
			{
				preis = preis.substring(0, preis.indexOf(",")+3);
			}
			if ((preis.length()) - (preis.indexOf(",")) == 3) 
			{
				
			}
			if ((preis.length()) - (preis.indexOf(",")) == 2) 
			{
				preis = preis + "0";
			}
			preis = preis.replace(",", "");
		}

		EditText zut = (EditText) findViewById(R.id.tx_newangebot_zutaten);
		String zutaten = zut.getText().toString();

		Angebot neuesAngebot = new Angebot();
		neuesAngebot.setTitel(artikelname);
		neuesAngebot.setArt(kategorie);
		neuesAngebot.setZutaten(zutaten);
		neuesAngebot.setPreis(Integer.parseInt(preis) * 100);

		if (!neuesAngebot.isInputValid()) {
			Toast.makeText(this, "Bitte alle Felder ausfüllen",
					Toast.LENGTH_LONG).show();
			return;
		}

		new NewAngebotAsyncTask().execute(neuesAngebot);
	}

	private class NewAngebotAsyncTask extends AsyncTask<Angebot, Void, Integer> {

		@Override
		protected Integer doInBackground(Angebot... params) {
			NetClient netClient = new NetClient();
			return netClient.createAngebot(params[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 201) {
				Toast.makeText(newAngebot.this.getApplicationContext(),
						"Das Angebot wurde erfolgreich eingetragen",
						Toast.LENGTH_LONG);
				startActivity(new Intent(newAngebot.this, MainActivity.class));
			} else {
				Toast.makeText(
						newAngebot.this,
						"Das Angebot konnte nicht eingetragen werden. Code = "
								+ result, Toast.LENGTH_LONG).show();
			}
		}
	}
}
