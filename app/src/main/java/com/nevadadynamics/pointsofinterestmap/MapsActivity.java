package com.nevadadynamics.pointsofinterestmap;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Null if Google Play services APK is not available.
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        try {
            setUpMapIfNeeded();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            setUpMapIfNeeded();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
    }

    private void setUpMapIfNeeded() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {

        // Build place picker with intent builder
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        Context context = getApplicationContext();
        startActivityForResult(builder.build(context), PLACE_PICKER_REQUEST);

        //Place place = PlacePicker.getPlace(android.content.Intent, context);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName(), "ID: ", place.getId());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                // Build Single Map
                mMap.addMarker(new MarkerOptions().position((place.getLatLng())).title(place.getName().toString()));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(place.getLatLng(), 19, 45, 90)));
/*

                // Log written data
                Log.d("File", "\n"
                        + "Name: " + place.getName().toString() + "\n"
                        + "Address: " + place.getAddress().toString() + "\n"
                        + "Phone: " + place.getPhoneNumber().toString() + "\n"
                        + "Website" + place.getWebsiteUri().toString() + "\n"
                        + place.getLatLng().toString() + "\n");

*/
            }
        }
    }

}
