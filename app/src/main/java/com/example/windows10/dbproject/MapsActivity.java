package com.example.windows10.dbproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DbHandler dbHandler;
    private double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        dbHandler = new DbHandler(this,null,null,1);

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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        dbTOMap();
    }

    public void dbTOMap(){
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        String query = "select * from crime where 1";
        String title,description;
        double lat,lon;

        Cursor cur = db.rawQuery(query,null);
        cur.moveToFirst();

        while (!cur.isAfterLast()){
            if (cur.getString(cur.getColumnIndex("title"))!=null){
                title=cur.getString(cur.getColumnIndex("title"));
                description = cur.getString(cur.getColumnIndex("description"));
                lat= Double.parseDouble(cur.getString(cur.getColumnIndex("lat")));
                lon= Double.parseDouble(cur.getString(cur.getColumnIndex("lon")));
                LatLng location = new LatLng(lat,lon);
                mMap.addMarker(new MarkerOptions().position(location).title(title).snippet(description));

//                //Make Infowindow
//                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                    @Override
//                    public View getInfoWindow(Marker marker) {
//                        return null;
//                    }
//
//                    @Override
//                    public View getInfoContents(Marker marker) {
//                        View v = getLayoutInflater().inflate(R.layout.infowindow,null);
//                        TextView info_title = (TextView) v.findViewById(R.id.info_title);
//                        TextView info_description = (TextView) v.findViewById(R.id.info_description);
//                        info_title.setText(marker.getTitle());
//                        info_description.setText(marker.getSnippet());
//                        return v;
//                    }
//                });


            }
            cur.moveToNext();

        }


    }
}
