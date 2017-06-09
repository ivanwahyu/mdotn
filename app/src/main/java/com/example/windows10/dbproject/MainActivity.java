package com.example.windows10.dbproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText titleinput,descriptioninput,latinput,loninput;
    TextView dbView;
    DbHandler dbHandler;
    private static final int REQUEST_CODE_LOCATION = 111;
    private double[] posisi = {0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleinput = (EditText) findViewById(R.id.title);
        descriptioninput = (EditText) findViewById(R.id.description);
        latinput = (EditText) findViewById(R.id.lat);
        loninput = (EditText) findViewById(R.id.lon);
        dbView = (TextView) findViewById(R.id.dbcontain);
        dbHandler = new DbHandler(this,null,null,1);
        printDatabase();
    }

    private void printDatabase() {
        String dbString = dbHandler.databaseToString();
        dbView.setText(dbString);
        titleinput.setText("");
        descriptioninput.setText("");
        latinput.setText("");
        loninput.setText("");
    }

    //Button add News
    public void addNewsButton(View view){
        News news = new News(titleinput.getText().toString(),descriptioninput.getText().toString(),latinput.getText().toString(),loninput.getText().toString());
        dbHandler.addNews(news);
        Log.d("0","berhasil masuk database");
        printDatabase();
    }

    //Button delete News
    public void deleteNewsButton(View view){
        String deletetitle = titleinput.getText().toString();
        dbHandler.deleteNews(deletetitle);
        printDatabase();
    }

    //To Map Activity use startActivityForResult
    public void toMap(View view){
        Intent i = new Intent(this,MapsActivity.class);
        startActivity(i);
    }

    //To Location Activity
    public void locationNews(View view){
        Intent i = new Intent(this,InputLocation.class);
        startActivityForResult(i,REQUEST_CODE_LOCATION);
    }

    //Get Result from InputLocation


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_LOCATION&&resultCode==RESULT_OK){
            posisi = data.getDoubleArrayExtra("posisi");
            Log.d("act 2 posisi 0 :", String.valueOf(posisi[0]));
            Log.d("act 2 posisi 1 :", String.valueOf(posisi[1]));
            latinput.setText(String.valueOf(posisi[0]));
            loninput.setText(String.valueOf(posisi[1]));
        }
    }
}
