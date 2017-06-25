package com.example.windows10.dbproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        title = (TextView) findViewById(R.id.title_tujuan);
        title.setText(getIntent().getStringExtra("crime details"));
    }
}
