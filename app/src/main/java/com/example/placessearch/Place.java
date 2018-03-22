package com.example.placessearch;

/**
 * Object to store place information
 */

public class Place {
    private String name;
    private double rating;
    private int price_level;
    private String place_id;
//    private String[] types;

    public Place(String name, double rating, int priceLevel, String placeID) {
        this.name = name;
        this.rating = rating;
        price_level = priceLevel;
        place_id = placeID;
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

//    public String[] getTypes() {
//        return types;
//    }
}
