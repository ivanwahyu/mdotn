package com.example.windows10.dbproject;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText titleinput,descriptioninput,latinput,loninput,timepick;
    TextView dbView;
    DbHandler dbHandler;
    String timeinput;
    private static final int REQUEST_CODE_LOCATION = 111;
    private double[] posisi = {0,0};
    private DatePickerDialog datePickerDialog;
    @TargetApi(Build.VERSION_CODES.N)
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
        timeinput="";
    }

    //Button inputTime
    public void TimeCrime (View view){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        timeinput= String.valueOf(dayOfMonth+"/"+monthOfYear+"/"+year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    //Button add Crime
    public void addCrimeButton(View view){
        Crime crime = new Crime(titleinput.getText().toString(),descriptioninput.getText().toString(),latinput.getText().toString(),loninput.getText().toString(),timeinput.toString());
        dbHandler.addCrime(crime);
        Log.d("0","berhasil masuk database");
        printDatabase();
    }

    //Button delete Crime
    public void deleteCrimeButton(View view){
        String deletetitle = titleinput.getText().toString();
        dbHandler.deleteCrime(deletetitle);
        printDatabase();
    }

    //To Map Activity
    public void toMap(View view){
        Intent i = new Intent(this,MapsActivity.class);
        startActivity(i);
    }

    //To Recycler Activity
    public void toRecycler(View view){
        Intent i = new Intent(this,RecyclerActivity.class);
        startActivity(i);
    }

    //To Location Activity
    public void locationCrime(View view){
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
