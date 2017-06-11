package com.example.windows10.dbproject;

/**
 * Created by Windows 10 on 07/06/2017.
 */

public class Crime {
    private String _title,_lat,_lon,_description;
    private int _id;

    public Crime() {
    }

    public Crime(String title, String description, String lat, String lon) {
        this._title = title;
        this._lat = lat;
        this._lon = lon;
        this._description = description;
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
}
