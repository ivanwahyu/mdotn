package com.example.windows10.dbproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DbHandler dbHandler;

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        dbTOMap();
    }

    public void dbTOMap(){
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        String query = "select * from news where 1";
        String title;
        double lat,lon;

        Cursor cur = db.rawQuery(query,null);
        cur.moveToFirst();

        while (!cur.isAfterLast()){
            if (cur.getString(cur.getColumnIndex("title"))!=null){
                title=cur.getString(cur.getColumnIndex("title"));
                lat= Double.parseDouble(cur.getString(cur.getColumnIndex("lat")));
                lon= Double.parseDouble(cur.getString(cur.getColumnIndex("lon")));
                LatLng location = new LatLng(lat,lon);
                mMap.addMarker(new MarkerOptions().position(location).title(title));
            }
            cur.moveToNext();

        }
    }
}
