package com.detroitlabs.icandigit.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.services.InventoryService;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class DigFragment extends Fragment implements LocationListener{

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private String locationProvider;
    private final int minTime = 1000;
    private final int minDistance = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        setUpMapIfNeeded();

        initializeLocationManager();

        Button button = (Button) rootView.findViewById(R.id.button_digit);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                InventoryService.startDig();

        //This came from Android Developer Docs, but didn't work too well for me.
//
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//
//
//                // Create new fragments and transaction
//                DugDialogFragment dugDialogFragment = new DugDialogFragment();
//
//                // Commit the transaction
//                fragmentTransaction.add(R.id.fragment_container, dugDialogFragment);
//                dugDialogFragment.show(fragmentTransaction, "not sure what this should be");
////                fragmentTransaction.commit();


                // Created a new Dialog
                Dialog dialog = new Dialog(getActivity());

// Set the title
                dialog.setTitle(R.string.you_found);

// inflate the layout
                dialog.setContentView(R.layout.dialog_view);

// Set the dialog text -- this is better done in the XML
                TextView text = (TextView)dialog.findViewById(R.id.dialog_text_view);
                text.setText("I can't tell you just...yet...because it's a secret! Yeah!");

// Display the dialog
                dialog.show();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);

        locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        /* Disable the my-location layer (this causes our LocationSource to be automatically deactivated.) */
//        googleMap.setMyLocationEnabled(false);
        locationManager.removeUpdates(this);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (googleMap != null) {
                // The Map is verified. It is now safe to manipulate the map.
            }
        }

    }

    private void centerMapOnMyLocation() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), (float)18.5));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(18)                  // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(0)                  // Sets the tilt of the camera to 30 degrees
                    .build();                  // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }

    private void initializeLocationManager() {

        //get the location manager
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);


        //define the location manager criteria
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        locationProvider = locationManager.getBestProvider(criteria, false);

        Location location = locationManager.getLastKnownLocation(locationProvider);


        //initialize the location
        if(location != null) {

            onLocationChanged(location);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("called", "onLocationChanged");


        //when the location changes, update the map by zooming to the location
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
        googleMap.moveCamera(center);

        CameraUpdate zoom=CameraUpdateFactory.zoomTo((float)18.5);
        googleMap.animateCamera(zoom);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
