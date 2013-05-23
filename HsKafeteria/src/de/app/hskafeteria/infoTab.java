package de.app.hskafeteria;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class infoTab extends Fragment {
	
    private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;
    
    static final LatLng CAFETERIA = new LatLng(49.01578, 8.39137);
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.info, container, false);

        try {
            MapsInitializer.initialize(getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO handle this situation
        }

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        setUpMap(inflatedView);

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

        mMap.addMarker(new MarkerOptions()
                                  .position(CAFETERIA)
                                  .title("Cafeteria")
                                  .snippet("Hochschule Karlsruhe"));
        
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CAFETERIA));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f)); 
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
