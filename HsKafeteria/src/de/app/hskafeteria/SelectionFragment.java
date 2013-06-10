package de.app.hskafeteria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import de.app.hskafeteria.httpclient.client.NetClient;
import de.app.hskafeteria.httpclient.domain.Benutzer;

public class SelectionFragment extends Fragment {

	private static final String TAG = "SelectionFragment";

	private TextView userNameView;

	private static final int REAUTH_ACTIVITY_CODE = 100;
	
	String email = "";
	String vorname = "";
	String nachname = "";
	String passwort = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.selection, container, false);

		// Find the user's name view
		userNameView = (TextView) view.findViewById(R.id.selection_user_name);
		// Check for an open session
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
		}
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a
		// new callback to handle the response.
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// If the response is successful
						if (session == Session.getActiveSession()) {
							if (user != null) {
								
								// Set the Textview's text to the user's name.
								String email = (String) user.asMap().get(
										"email");
								String vorname = user.getFirstName();
								String nachname = user.getLastName();
								
								new LoginAsyncTask().execute(email, vorname, nachname);
								
								NetClient nc = new NetClient();
								int codeLogin = nc.login(email, passwort);
								int codePut = 201;
								if (codeLogin != 200) {

								}
								if (codePut == 201) {
									
								}
							}
						}
						if (response.getError() != null) {
							// Handle errors, will do so later.
						}
					}
				});
		request.executeAsync();
	}

	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			// Get the user's data.
			makeMeRequest(session);
		}
	}

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE) {
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	private class LoginAsyncTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			String email = params[0];
			String vorname = params[1];
			String nachname = params[2];
			String passwort = "fblogin";
			NetClient netClient = new NetClient();
					
			return netClient.login(email, passwort);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result != 200) {
				new CreateUserAsyncTask().execute(email, vorname, nachname);
			}
			if (result == 200) {
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getActivity()
								.getBaseContext());
				prefs.edit()
						.putString("logged_in_user", email)
						.commit();

				prefs.edit()
						.putString("logged_in_user_name",
								vorname + " " + nachname)
						.commit();
				startActivity(new Intent(getActivity()
						.getBaseContext(),
						MainActivity.class));
			}

		}

	}
	
	private class CreateUserAsyncTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			String email = params[0];
			String vorname = params[1];
			String nachname = params[2];
			String passwort = "fblogin";
			NetClient netClient = new NetClient();
					
			return netClient.createNewBenutzer(benutzer)
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result != 200) {
				Benutzer fbBenutzer = new Benutzer();
				fbBenutzer.setVorname(vorname);
				fbBenutzer.setNachname(nachname);
				fbBenutzer.setEmail(email);
				fbBenutzer.setPasswort(passwort);
				
				Benutzer fbBenutzer = new Benutzer();
				fbBenutzer.setVorname(vorname);
				fbBenutzer.setNachname(nachname);
				fbBenutzer.setEmail(email);
				fbBenutzer.setPasswort(passwort);
				
				new CreateUserAsyncTask().execute(email, vorname, nachname);
			}
			if (result == 200) {
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getActivity()
								.getBaseContext());
				prefs.edit()
						.putString("logged_in_user", email)
						.commit();

				prefs.edit()
						.putString("logged_in_user_name",
								vorname + " " + nachname)
						.commit();
				startActivity(new Intent(getActivity()
						.getBaseContext(),
						MainActivity.class));
			}

		}

	}
}
