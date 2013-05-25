package de.app.hskafeteria;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends Activity {

	private Context ctx;
	
    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        ctx = getBaseContext();
	        	 
	        ActionBar actionBar = getActionBar();
	 
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	 
	        String nav_news = getResources().getString(R.string.nav_news);
	        Tab tab = actionBar.newTab();
	        tab.setText(nav_news);
	        TabListener<newsTab> tl1 = new TabListener<newsTab>(this,
	                nav_news, newsTab.class);
	        tab.setTabListener(tl1);
	        actionBar.addTab(tab);
	 
	        String nav_angebote = getResources().getString(R.string.nav_angebote);
	        tab = actionBar.newTab();
	        tab.setText(nav_angebote);
	        TabListener<angeboteTab> tl2 = new TabListener<angeboteTab>(this,
	        		nav_angebote, angeboteTab.class);
	        tab.setTabListener(tl2);
	        actionBar.addTab(tab);
	        
	        String nav_aktionen = getResources().getString(R.string.nav_aktionen);
	        tab = actionBar.newTab();
	        tab.setText(nav_aktionen);
	        TabListener<aktionenTab> tl3 = new TabListener<aktionenTab>(this,
	        		nav_aktionen, aktionenTab.class);
	        tab.setTabListener(tl3);
	        actionBar.addTab(tab);
	        
	        String nav_info = getResources().getString(R.string.nav_info);
	        tab = actionBar.newTab();
	        tab.setText(nav_info);
	        TabListener<infoTab> tl4 = new TabListener<infoTab>(this,
	        		nav_info, infoTab.class);
	        tab.setTabListener(tl4);
	        actionBar.addTab(tab);
	 
	    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main, menu);
    	
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
    	
    	String LoggedInUserEmail = prefs.getString("logged_in_user", "");
    	
    	if(LoggedInUserEmail.equals(""))
    	{
        	MenuItem logout = menu.findItem(R.id.logout);
        	logout.setVisible(false);
        	
        	MenuItem settings = menu.findItem(R.id.settings);
        	settings.setVisible(false);
        	
        	MenuItem insert = menu.findItem(R.id.insert);
        	insert.setVisible(false);
    	}
    	
    	else if(LoggedInUserEmail.equals("admin@admin.com"))
    	{
        	MenuItem login = menu.findItem(R.id.login);
        	login.setVisible(false);
    	}
    	
    	else
    	{
        	MenuItem login = menu.findItem(R.id.login);
        	login.setVisible(false);
        	
        	MenuItem insert = menu.findItem(R.id.insert);
        	insert.setVisible(false);
    	}
    	
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.login:
			startActivity(new Intent(this, Login.class));
			return true;
		case R.id.logout:
			showConfirmationDialog();
			return true;
		case R.id.settings:
			startActivity(new Intent(this, Settings.class));
			return true;
		case R.id.insert:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }
    
	private void showConfirmationDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set title
		alertDialogBuilder.setTitle("Möchten Sie sich wirklich abmelden?");
		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
				    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
				    	prefs.edit().putString("logged_in_user", "").commit();

				    	finish();
				    	startActivity(getIntent());
					}
				})
				.setNegativeButton("Nein",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
}
