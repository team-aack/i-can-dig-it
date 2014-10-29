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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DigFragment extends Fragment implements LocationListener{

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private String locationProvider;
    private final int minTime = 1000; //time between userLocation updates in milliseconds
    private final int minDistance = 1; //distance required to move to update userLocation
    private ArrayList<Marker> listOfHoleMarkers = new ArrayList<Marker>();
    private Button digButton;
    private Button inventoryButton;
    Marker littleRedHuman;
    public static final String LOG_TAG = DigFragment.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        setUpMapIfNeeded();

        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);

        initializeLocationManager();

        locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, this);


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });

        // Create new fragments and transaction

        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        final YouFoundFragment youFoundFragment = new YouFoundFragment();
        final BkgButtonFragment bkgButtonFragment = new BkgButtonFragment();

        if (fragmentTransaction.isEmpty()) {


            // Commit the transaction
            fragmentTransaction.add(R.id.fragment_container2, youFoundFragment);
            fragmentTransaction.add(R.id.fragment_container, bkgButtonFragment);
            Log.v(LOG_TAG, "*********put the YouFoundFragment and BkgButtonFragment at the top");
            fragmentTransaction.commit();
            Log.v(LOG_TAG, "********COMMITTING!!");

        }

        digButton = (Button) rootView.findViewById(R.id.button_digit);
        digButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                InventoryService.startDig();

                listOfHoleMarkers.add(googleMap.addMarker
                        (new MarkerOptions()
                                .position(new LatLng(locationManager.getLastKnownLocation(locationProvider).getLatitude(),locationManager.getLastKnownLocation(locationProvider).getLongitude()))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.your_hole))));

                String newTreasure = InventoryService.freshTreasure.getItemType().toUpperCase();
                youFoundFragment.getRelativeLayout().setVisibility(View.VISIBLE);
                youFoundFragment.getTextView().setText(newTreasure);
                bkgButtonFragment.getButton().setVisibility(View.VISIBLE);
                    }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


        locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        /* Disable the my-userLocation layer (this causes our LocationSource to be automatically deactivated.) */
        googleMap.setMyLocationEnabled(false);
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
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to userLocation user
                    .zoom(18)                  // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(0)                  // Sets the tilt of the camera to 30 degrees
                    .build();                  // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }

    private void initializeLocationManager() {

        //get the userLocation manager
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);


        //define the userLocation manager criteria
        Criteria criteria = new Criteria();
        //criteria.setAccuracy(Criteria.ACCURACY_FINE);

        locationProvider = locationManager.getBestProvider(criteria, false);

        Location userLocation = locationManager.getLastKnownLocation(locationProvider);


        //initialize the userLocation
        if(userLocation != null) {

            onLocationChanged(userLocation);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("called", "onLocationChanged");


        //when the userLocation changes, update the map by zooming to the userLocation
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
        googleMap.moveCamera(center);

        CameraUpdate zoom=CameraUpdateFactory.zoomTo((float)18.5);
        googleMap.animateCamera(zoom);

        if (littleRedHuman != null){
            littleRedHuman.remove();
        }


        littleRedHuman = (googleMap.addMarker
                (new MarkerOptions()
                        .position(new LatLng(locationManager.getLastKnownLocation(locationProvider).getLatitude(),locationManager.getLastKnownLocation(locationProvider).getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.your_location))));
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
