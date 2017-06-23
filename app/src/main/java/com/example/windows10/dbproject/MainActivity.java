package com.example.windows10.dbproject;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    byte[] image;
    byte imageInByte[];
    Bitmap theImage;
    Button addImage;
    ImageView imageView;

    public static final int IMAGEREQUESTCODE = 1;

    EditText titleinput,descriptioninput,latinput,loninput;

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
        addImage = (Button) findViewById(R.id.inputImage);
        dbHandler = new DbHandler(this,null,null,1);
        imageView = (ImageView) findViewById(R.id.imageView);
        printDatabase();

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, IMAGEREQUESTCODE);
            }
        });

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
        Crime crime = new Crime(titleinput.getText().toString(),descriptioninput.getText().toString(),latinput.getText().toString(),loninput.getText().toString(),timeinput.toString(),imageInByte);
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
        if (requestCode==IMAGEREQUESTCODE&&resultCode==RESULT_OK) {
                manageImageFromUri(data.getData());
        }
    }

    private void manageImageFromUri(Uri imageUri) {
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(), imageUri);
            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageInByte = stream.toByteArray();
            Log.d("compress", String.valueOf(imageInByte));

        } catch (Exception e) {
            // Manage exception ...
        }

        if (bitmap != null) {
            // Here you can use bitmap in your application ...
        }
    }

    //open camera method
    public void callCamera() {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("crop", "true");
        cameraIntent.putExtra("aspectX", 0);
        cameraIntent.putExtra("aspectY", 0);
        cameraIntent.putExtra("outputX", 200);
        cameraIntent.putExtra("outputY", 150);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    //open gallery method
    public void callGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);

    }
}
