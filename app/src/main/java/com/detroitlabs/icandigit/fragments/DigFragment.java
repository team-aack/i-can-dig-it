package com.detroitlabs.icandigit.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.objects.DigSite;
import com.detroitlabs.icandigit.objects.Treasure;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private Timer myTimer;
    private final long TIMER_DELAY = 0;
    private final long TIMER_PERIOD = 1000;
    private ArrayList<Marker> digSiteMarkerList;
    public static final String LOG_TAG = DigFragment.class.getSimpleName();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, TIMER_DELAY, TIMER_PERIOD);

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
        final BkgButtonFragment bkgButtonFragment = new BkgButtonFragment();

        if (fragmentTransaction.isEmpty()) {


            // Commit the transaction
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

                //adds a marker (containing a position constructed from a LatLng built from the latitude and longitude of the users last recorded location) to the google map
                //also stores the marker to digSiteMarker
                digSiteMarker = googleMap.addMarker
                        (new MarkerOptions()
                                .position(new LatLng(locationManager.getLastKnownLocation(locationProvider).getLatitude(),locationManager.getLastKnownLocation(locationProvider).getLongitude()))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.your_hole))); //changes the icon used on this

                digSiteTimeStamp = System.currentTimeMillis(); //current time in milliseconds since midnight UTC on the 1st of January 1970

                listOfDigSites.add(new DigSite(digSiteTimeStamp, digSiteMarker.getPosition().latitude, digSiteMarker.getPosition().longitude));
                digSiteMarkerList.add(digSiteMarker);

                String freshTreasure = InventoryService.freshTreasure.getItemType().toUpperCase();
                bkgButtonFragment.getButton().setVisibility(View.VISIBLE);
                bkgButtonFragment.getRelativeLayout().setVisibility(View.VISIBLE);
                bkgButtonFragment.getTextView().setText(freshTreasure);
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

        String digJson = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("listOfDigSites", "empty");
        String treasureJson = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("listOfTreasures", "empty");

        if (digJson != "empty") {
            Type digSiteType = new TypeToken<ArrayList<DigSite>>(){}.getType();
            listOfDigSites = gson.fromJson(digJson, digSiteType);
        }

        for(DigSite currentSite: listOfDigSites){
            Marker mostRecentlyCreatedMarker = googleMap.addMarker
                    (new MarkerOptions()
                            .position(new LatLng(currentSite.getLat(),currentSite.getLng()))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.your_hole))); //changes the icon used on this

            digSiteMarkerList.add(mostRecentlyCreatedMarker);
        }

        if (treasureJson != "empty") {
            Type treasureType = new TypeToken<ArrayList<Treasure>>(){}.getType();
            List<Treasure> treasureList = gson.fromJson(treasureJson, treasureType);
            InventoryService.setItemInventory(treasureList);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        /* Disable the my-userLocation layer (this causes our LocationSource to be automatically deactivated.) */
        googleMap.setMyLocationEnabled(false);
        locationManager.removeUpdates(this);

        Gson gson = new Gson();

        String digJson = gson.toJson(listOfDigSites);
        String treasureJson = gson.toJson(InventoryService.getItemInventory());

        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .edit()
                .putString("listOfDigSites", digJson)
                .commit();

        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .edit()
                .putString("listOfTreasures", treasureJson)
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

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        getActivity().runOnUiThread(Timer_Tick);
    }


    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.

            //Do something to the UI thread here

        }
    };
}


