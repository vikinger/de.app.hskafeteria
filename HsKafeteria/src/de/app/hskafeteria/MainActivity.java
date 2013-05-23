package de.app.hskafeteria;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	        StrictMode.setThreadPolicy(policy); 
	 
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_login:
        startActivity(new Intent(this, Login.class));
        return true;
        default:
        return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
}
