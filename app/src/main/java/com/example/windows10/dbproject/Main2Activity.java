package com.example.windows10.dbproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
    public void listicon(View view){
        Intent intent = new Intent(this,RecyclerActivity.class);
        startActivity(intent);
    }
    public void mapicon(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void addicon(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
