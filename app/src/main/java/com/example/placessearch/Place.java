package com.example.placessearch;

import android.graphics.Bitmap;

/**
 * Object to store place information
 */

public class Place {
    private String name;
    private double rating;
    private int price_level;
    private String place_id;
    private String url;
//    private String[] types;

    public Place(String name, double rating, int priceLevel, String placeID, String url) {
        this.name = name;
        this.rating = rating;
        price_level = priceLevel;
        place_id = placeID;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public int getPriceLevel() {
        return price_level;
    }

    public String getPlaceID() {
        return place_id;
    }

    public String getPhoto() {
        return url;
    }
}
