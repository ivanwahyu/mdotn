package com.example.windows10.dbproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CrimesAdapter adapter;
    private List<Crime> crimeList;
    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //add button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //add database
        dbHandler = new DbHandler(this,null,null,1);

        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        crimeList = new ArrayList<>();
        adapter = new CrimesAdapter(this,crimeList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareCrimes();
    }


    private void prepareCrimes() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        String query = "select * from crime where 1";
        String title,description,time;
        byte[] image;
        double lat,lon;
        Crime crm;

        Cursor cur = db.rawQuery(query,null);
        cur.moveToFirst();

        while (!cur.isAfterLast()) {
            if (cur.getString(cur.getColumnIndex("title")) != null) {
                title = cur.getString(cur.getColumnIndex("title"));
                description = cur.getString(cur.getColumnIndex("description"));
                lat = Double.parseDouble(cur.getString(cur.getColumnIndex("lat")));
                lon = Double.parseDouble(cur.getString(cur.getColumnIndex("lon")));
                time = cur.getString(cur.getColumnIndex("time"));
                image = cur.getBlob(cur.getColumnIndex("image"));

                crm = new Crime(title,description,String.valueOf(lat),String.valueOf(lon),String.valueOf(time),image);
                crimeList.add(crm);
            }
            cur.moveToNext();
        }
        adapter.notifyDataSetChanged();
    }

}
