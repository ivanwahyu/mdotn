package com.example.windows10.dbproject;

/**
 * Created by Windows 10 on 07/06/2017.
 */

public class Crime {
    private String _title,_lat,_lon,_description,_time;
    private int _id;
    private byte[] _image;


    public Crime() {
    }

    public Crime(String title, String description, String lat, String lon, String time, byte[] image) {
        this._title = title;
        this._lat = lat;
        this._lon = lon;
        this._time = time;
        this._description = description;
        this._image = image;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_lat() {
        return _lat;
    }

    public void set_lat(String _lat) {
        this._lat = _lat;
    }

    public String get_lon() {
        return _lon;
    }

    public void set_lon(String _lon) {
        this._lon = _lon;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }
}
