package com.example.windows10.dbproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Windows 10 on 07/06/2017.
 */

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "news.db";
    public static final String TABLE_CRIME = "crime";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LON = "lon";
    public static final String COLUMN_TIME = "time";

    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CRIME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_LAT + " TEXT, " +
                COLUMN_LON + " TEXT, " +
                COLUMN_TIME + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRIME);
        onCreate(db);
    }

    //Add new Crime
    public void addCrime(Crime crime){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, crime.get_title());
        values.put(COLUMN_DESCRIPTION, crime.get_description());
        values.put(COLUMN_LAT, crime.get_lat());
        values.put(COLUMN_LON, crime.get_lon());
        values.put(COLUMN_TIME, crime.get_time());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CRIME,null,values);
        db.close();
    }

    //Delete a Crime
    public void deleteCrime(String title){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CRIME + " WHERE " + COLUMN_TITLE + "=\"" + title + "\";");
    }

    //Print out the database String
    public String databaseToString(){
        String dbString ="";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CRIME + " WHERE 1";

        //Cursor point to location in your results
        Cursor recordSet = db.rawQuery(query,null);
        recordSet.moveToFirst();

        while (!recordSet.isAfterLast()){
            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_TITLE))!=null){
                dbString +=recordSet.getString(recordSet.getColumnIndex(COLUMN_TITLE))+" "+
                        recordSet.getString(recordSet.getColumnIndex(COLUMN_DESCRIPTION))+" "+
                        recordSet.getString(recordSet.getColumnIndex(COLUMN_TIME))+" "+
                        recordSet.getString(recordSet.getColumnIndex(COLUMN_LAT))+" "+
                        recordSet.getString(recordSet.getColumnIndex(COLUMN_LON));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }
}















