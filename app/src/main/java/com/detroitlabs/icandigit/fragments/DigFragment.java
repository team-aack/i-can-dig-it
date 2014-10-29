package com.detroitlabs.icandigit.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.objects.DigSite;
import com.detroitlabs.icandigit.services.InventoryService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DigFragment extends Fragment implements LocationListener{

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private String locationProvider;
    private final int minTime = 1000; //time between userLocation updates in milliseconds
    private final int minDistance = 1; //distance required to move to update userLocation
    private ArrayList<DigSite> listOfDigSites = new ArrayList<DigSite>();
    private Button digButton;
    private Marker littleRedHuman;
    private Marker digSiteMarker;
    private long digSiteTimeStamp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

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

        digButton = (Button) rootView.findViewById(R.id.button_digit);
        digButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                InventoryService.startDig();

                digSiteTimeStamp = System.currentTimeMillis(); //current time in milliseconds since midnight UTC on the 1st of January 1970

                //adds a marker (containing a position constructed from a LatLng built from the latitude and longitude of the users last recorded location) to the google map
                //also stores the marker to digSiteMarker
                digSiteMarker = googleMap.addMarker
                        (new MarkerOptions()
                                .position(new LatLng(locationManager.getLastKnownLocation(locationProvider).getLatitude(),locationManager.getLastKnownLocation(locationProvider).getLongitude()))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.your_hole))); //changes the icon used on this

                listOfDigSites.add(new DigSite(digSiteTimeStamp, digSiteMarker.getPosition().latitude, digSiteMarker.getPosition().longitude));

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
                text.setText(InventoryService.freshTreasure.getItemType() + "!");

// Display the dialog
                dialog.show();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, this);

        //retrieves string containing json data from shared preferences and gets object from it
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("listOfDigSites", "empty");
        if (json != "empty") {
            Type digSiteType = new TypeToken<ArrayList<DigSite>>(){}.getType();
            listOfDigSites = gson.fromJson(json, digSiteType);
        }
        for(DigSite currentSite: listOfDigSites){
            googleMap.addMarker
                    (new MarkerOptions()
                            .position(new LatLng(currentSite.getLat(),currentSite.getLng()))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.your_hole))); //changes the icon used on this
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        /* Disable the my-userLocation layer (this causes our LocationSource to be automatically deactivated.) */
        googleMap.setMyLocationEnabled(false);
        locationManager.removeUpdates(this);
        Gson gson = new Gson();
        String json = gson.toJson(listOfDigSites);
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .edit()
                .putString("listOfDigSites", json)
                .commit();
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
                        .position(new LatLng(locationManager.getLastKnownLocation(locationProvider).getLatitude(), locationManager.getLastKnownLocation(locationProvider).getLongitude()))
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