package com.example.windows10.dbproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {
    CollapsingToolbarLayout detail_title;
    TextView title,time,description ;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        detail_title = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        time=(TextView) findViewById(R.id.detail_time);
        description=(TextView) findViewById(R.id.detail_description);
        image = (ImageView) findViewById(R.id.detail_image);

        detail_title.setTitle(getIntent().getStringExtra("crime title"));
        time.setText(getIntent().getStringExtra("crime time"));
        description.setText(getIntent().getStringExtra("crime description"));
        byte[] outImage= getIntent().getByteArrayExtra("crime image");
        Bitmap theImage = BitmapFactory.decodeByteArray(outImage, 0, outImage.length);
        image.setImageBitmap(theImage);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
