package com.detroitlabs.icandigit.fragments;

import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.services.InventoryService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class DigFragment extends Fragment {

    private GoogleMap googleMap;
    private Location location;
    private LatLng myLatLng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, null);

        setUpMapIfNeeded();

        //makes it so that our map displays terrain
        //googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        centerMapOnMyLocation();

        Button button = (Button) rootView.findViewById(R.id.button_digit);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                InventoryService.startDig();

            }
        });

        return rootView;
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
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(18)                  // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(0)                  // Sets the tilt of the camera to 30 degrees
                    .build();                  // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }
}