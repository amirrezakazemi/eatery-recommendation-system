package com.example.androidfinalproject.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.example.androidfinalproject.controller.DataProvider;
import com.example.androidfinalproject.controller.MarginDecoration;
import com.example.androidfinalproject.model.MyPreferenceManager;
import com.example.androidfinalproject.model.Place;
import com.example.androidfinalproject.controller.PlaceAdapter;
import com.example.androidfinalproject.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.util.ArrayList;

/* this class implements PlaceAdapter.ItemClickListener(interface) because we want to respond to click events here in main
   Activity by dataProvider we defined here, this interface has a method "OnItemClick" and we override it here
*/
public class MainActivity extends Activity implements PlaceAdapter.ItemClickListener {


    DataProvider dataProvider;
    RecyclerView recyclerView;
    PlaceAdapter placeAdapter;
    ArrayList<Place> places;
    MyPreferenceManager myPreferenceManager;

    private LocationCallback mLocationCallback;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
        myPreferenceManager = new MyPreferenceManager(getApplicationContext());
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new MarginDecoration(this));
        places = new ArrayList<>();
        getPermission();
        double[] ll = new double[2];
        if (myPreferenceManager.loadLat() != null) {
            // we need to cast to double because the function output is String type as we saved them in SharedPreference
            ll = new double[]{Double.parseDouble(myPreferenceManager.loadLat()), Double.parseDouble(myPreferenceManager.loadLong())};
        }

        // there is an interface in DataProvider class , DataReadyListener . when instantiating this method we override
        // onDataReady method in that interface.
        dataProvider = new DataProvider(this, new DataProvider.DataReadyListener() {
            @Override
            public void onDataReady(ArrayList<Place> venues) {
                placeAdapter.update(venues);
            }
        });
        // when first Activity created , we need to show places so we call this method in OnCreate.
        dataProvider.fetchData(ll);


        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationCallback = new LocationCallback() {
            ////////// this method listens to location changes and fetch data for last location
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (dataProvider.getCurrentLocation() != null && location.distanceTo(dataProvider.getCurrentLocation()) > 100) {
                        dataProvider.fetchData(new double[]{location.getLatitude(), location.getLongitude()});
                        System.out.println("---------------------------- update");
                        System.out.println("============= location is getting updated :|");
                    }
                }
            }
        };

        placeAdapter = new PlaceAdapter(getApplicationContext(), places);
        placeAdapter.setClickListener(this);
        recyclerView.setAdapter(placeAdapter);
        /* there is a inner Class in RecyclerView named OnScrollListener , we instantiate this by overriding methods and
           pass this object to addOnScrollListener method on RecyclerView , by overriding these 2 methods we are able to
           get more places from DataProvider
        */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Recycle view scrolling down...
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        // if we cant scroll vertically then it means we should get more venues
                        if (placeAdapter.getItemCount() > 0) {
                            dataProvider.getMoreVenues();
                            Toast.makeText(getApplicationContext(), "درحال بروز رسانی", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataProvider.getCurrentLocation() != null) {
            myPreferenceManager.saveLat(dataProvider.getCurrentLocation().getLatitude());
            myPreferenceManager.saveLong(dataProvider.getCurrentLocation().getLongitude());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dataProvider.getCurrentLocation() != null) {
            myPreferenceManager.saveLat(dataProvider.getCurrentLocation().getLatitude());
            myPreferenceManager.saveLong(dataProvider.getCurrentLocation().getLongitude());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getPermission();
        // when we want to resume we call this method to update the location
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            getPermission();
            return;
        } else {

            dataProvider.getClient().requestLocationUpdates(locationRequest,
                    mLocationCallback,
                    null /* Looper */);
        }
    }

    // this method is for responding to click on a place and will show us the details of the place
    // by starting a new activity
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getApplicationContext(), DetailedVenueActivity.class);
        intent.putExtra("id", dataProvider.getVenuesList().get(position).getId());
        intent.putExtra("name", dataProvider.getVenuesList().get(position).getName());
        startActivity(intent);
    }

    // this method request for location permission if it is not granted.
    void getPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

        } else {
            // do nothing
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    double[] pll = new double[2];
                    dataProvider.fetchData(pll);
                    System.out.println("---------------------------- permission");
                } else {
//                    getPermission();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
