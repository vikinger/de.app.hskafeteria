package de.app.hskafeteria;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import de.app.hskafeteria.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class infoTab extends Fragment {

	private MapView mMapView;
	private GoogleMap mMap;
	private Bundle mBundle;
	private double standortLatitude;
	private double standortLongitude;
	private String provider;
	private LocationManager service;
	private Boolean showRoute = false;
	private Float distance;
	private CheckedTextView viewDistance;

	static final LatLng CAFETERIA = new LatLng(49.01578, 8.39137);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View inflatedView = inflater.inflate(R.layout.info, container, false);

		try {
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			// TODO handle this situation
		}

		mMapView = (MapView) inflatedView.findViewById(R.id.map);
//		viewDistance = (CheckedTextView) inflatedView
//				.findViewById(R.id.CheckedTextView07);
		mMapView.onCreate(mBundle);
		setUpMap(inflatedView);
		

		final Button button = (Button) inflatedView.findViewById(id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				showRoute = true;
				final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
				final String lat = prefs.getString("Latitude", "");
				final String lon = prefs.getString("Longitude", "");
				final String name = prefs.getString("Longitude", "");
				
				standortLatitude = Double.parseDouble(lat);
				standortLongitude = Double.parseDouble(lon);
				
//				Intent intent = new Intent();
//				intent.setAction("infoTab");
//				
//				Bundle extras = intent.getExtras();
//				
////				Float lat = extras.getFloat("Latitude");
////				Float lon = extras.getFloat("Longitude");
//				String test = extras.getString("Test");
//				test = test;
//
				Location cafeteria = new Location("");
				cafeteria.setLatitude(49.01578);
				cafeteria.setLongitude(8.39137);

				Location start = new Location("");
				start.setLatitude(standortLatitude);
				start.setLongitude(standortLongitude);

				distance = cafeteria.distanceTo(start);

//				viewDistance.setText("Entfernung: " + distance/1000 + "km");

				setUpMap();
			}
		});

		return inflatedView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBundle = savedInstanceState;
	}

	private void setUpMap(View inflatedView) {
		mMap = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
		if (mMap != null) {
			setUpMap();
		}
	}

	private void setUpMap() {

		if (showRoute == false) {
			mMap.addMarker(new MarkerOptions().position(CAFETERIA)
					.title("Cafeteria").snippet("Hochschule Karlsruhe"));
			mMap.moveCamera(CameraUpdateFactory.newLatLng(CAFETERIA));
			mMap.animateCamera(CameraUpdateFactory.zoomTo(17.00f));
		}

		else if (showRoute == true) {			
			String uri = "http://maps.google.com/maps?saddr=" + standortLatitude+","+standortLongitude+"&daddr="+49.01578+","+8.39137;
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
			intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
			startActivity(intent);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onDestroy() {
		mMapView.onDestroy();
		super.onDestroy();
	}

}
