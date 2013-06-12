package de.app.hskafeteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;

import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.Benutzer;

public class Login extends FragmentActivity {

	private Context ctx;
	private String textEmail = null;
	private String nameOfUser = null;
	private ProgressDialog pDlg = null;
	
	private static final int SPLASH = 0;
	private static final int SELECTION = 1;

	private static final int SETTINGS = 2;
	private static final int FRAGMENT_COUNT = SETTINGS +1;

	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	
	private boolean isResumed = false;
	
	private MenuItem settings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = getBaseContext();
		setContentView(R.layout.activity_login);
		
		uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
	    
		 FragmentManager fm = getSupportFragmentManager();
		    fragments[SPLASH] = fm.findFragmentById(R.id.splashFragment);
		    fragments[SELECTION] = fm.findFragmentById(R.id.selectionFragment);
		    fragments[SETTINGS] = fm.findFragmentById(R.id.userSettingsFragment);

		    FragmentTransaction transaction = fm.beginTransaction();
		    for(int i = 0; i < fragments.length; i++) {
		        transaction.hide(fragments[i]);
		    }
		    transaction.commit();
	    
	    
		
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
	private void showFragment(int fragmentIndex, boolean addToBackStack) {
	    FragmentManager fm = getSupportFragmentManager();
	    FragmentTransaction transaction = fm.beginTransaction();
	    for (int i = 0; i < fragments.length; i++) {
	        if (i == fragmentIndex) {
	            transaction.show(fragments[i]);
	        } else {
	            transaction.hide(fragments[i]);
	        }
	    }
	    if (addToBackStack) {
	        transaction.addToBackStack(null);
	    }
	    transaction.commit();
	}
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	    isResumed = true;
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	    isResumed = false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    // Only make changes if the activity is visible
	    if (isResumed) {
	        FragmentManager manager = getSupportFragmentManager();
	        // Get the number of entries in the back stack
	        int backStackSize = manager.getBackStackEntryCount();
	        // Clear the back stack
	        for (int i = 0; i < backStackSize; i++) {
	            manager.popBackStack();
	        }
	        if (state.isOpened()) {
	        	Session.NewPermissionsRequest newPermissionsRequest = new Session
	                    .NewPermissionsRequest(this, Arrays.asList("email"));

	            session.requestNewReadPermissions(newPermissionsRequest);
	            // If the session state is open:
	            // Show the authenticated fragment
	            showFragment(SELECTION, false);

	        } else if (state.isClosed()) {
	            // If the session state is closed:
	            // Show the login fragment
	            showFragment(SPLASH, false);
	        }
	    }
	}
	@Override
	protected void onResumeFragments() {
	    super.onResumeFragments();
	    Session session = Session.getActiveSession();
	    

	    if (session != null && session.isOpened()) {
	        // if the session is already open,
	        // try to show the selection fragment
	        showFragment(SELECTION, false);
	    } else {
	        // otherwise present the splash screen
	        // and ask the person to login.
	        showFragment(SPLASH, false);
	    }
	}
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = 
	    new Session.StatusCallback() {
	    @Override
	    public void call(Session session, 
	            SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    // only add the menu when the selection fragment is showing
	    if (fragments[SELECTION].isVisible()) {
	        if (menu.size() == 0) {
	            settings = menu.add(R.string.settings);
	        }
	        return true;
	    } else {
	        menu.clear();
	        settings = null;
	    }
	    return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	        	super.onBackPressed();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	protected void anmelden() {
		showProgressDialog();
		
		EditText email = (EditText)findViewById(R.id.tx_login_email);
		textEmail = email.getText().toString();

		EditText passwort = (EditText)findViewById(R.id.tx_login_passwort);
		String textPassword = passwort.getText().toString();

		if (textEmail.equals("") || textPassword.equals("")) {
			Toast.makeText(this, "Bitte alle Felder ausfüllen",
					Toast.LENGTH_LONG).show();
			return;
		}
		new GetUserNameAsyncTask().execute(textEmail);

		
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
			pDlg.dismiss();
		}

	}
	
	private class GetUserNameAsyncTask extends AsyncTask<String, Void, Benutzer> {
		
		@Override
		protected Benutzer doInBackground(String... params) {
			String email = params[0];
			
			NetClient netClient = new NetClient();
			return netClient.getBenutzerByEmail(email);

		}
		
		@Override
		protected void onPostExecute(Benutzer result) {
			super.onPostExecute(result);
			if (result != null) {
				nameOfUser = result.getVorname() + " " + result.getNachname();
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
				prefs.edit().putString("logged_in_user_name", nameOfUser).commit();
			}
			else {
				Toast.makeText(Login.this, "Email/Passwort falsch", Toast.LENGTH_LONG).show();
			}
		}
	}
	
    private void showProgressDialog() {
        
        pDlg = new ProgressDialog(Login.this);
        pDlg.setMessage("Bitte warten...");
        pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDlg.setCancelable(false);
        pDlg.show();

    }
}


