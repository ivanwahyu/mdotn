package com.example.windows10.dbproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class InputLocation extends FragmentActivity implements OnMapReadyCallback, OnMapLongClickListener, OnMapClickListener, OnMarkerDragListener {

    private GoogleMap mMap;
    Context context;
    private double lat,lon;
    Button btnsavelocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnsavelocation = (Button) findViewById(R.id.btn_savelocation);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable MyLocation Layer of Google Map
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Add default find current location
        mMap.setMyLocationEnabled(true);
        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);
        lat = location.getLatitude();
        lon = location.getLongitude();
//        Log.d("nilai lat : ", String.valueOf(lat)+"===========================");
//        Log.d("nilai lon : ", String.valueOf(lon));

        // Add move the camera to current location
        LatLng latlang = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(latlang).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));
        Toast.makeText(this, "Open map",Toast.LENGTH_SHORT).show();

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerDragListener(this);


    }

    //button for close activity
    public void saveLocation(View view){
        double[] posisi = new double[]{lat, lon};
        Log.d("nilai lat : ", String.valueOf(lat)+"===========================");
        Log.d("nilai lon : ", String.valueOf(lon));
        // put the String to pass back into an Intent and close this activity
        Intent intent = new Intent();
        Log.d("act 2 posisi 0 :", String.valueOf(posisi[0]));
        Log.d("act 2 posisi 1 :", String.valueOf(posisi[1]));
        Log.d("array", String.valueOf(posisi));
        intent.putExtra("posisi", posisi);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        Toast.makeText(context,"Point : "+latLng,Toast.LENGTH_SHORT);
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

//        Toast.makeText(context,"New marker : "+latLng.toString(),Toast.LENGTH_SHORT);
//        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        lat = marker.getPosition().latitude;
        lon = marker.getPosition().longitude;

        Toast.makeText(this, String.valueOf(lat),Toast.LENGTH_SHORT).show();
        btnsavelocation.setVisibility(View.VISIBLE);

    }
}
