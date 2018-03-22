package com.example.placessearch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private final static String URL = "https://maps.googleapis" +
            ".com/maps/api/place/nearbysearch/json?";
    private final String API_KEY = "AIzaSyBBuiXIK0tDJgMUqHOqEyZugoN53_Bc_BY";
    private LocationManager mLocationManager;
    private Location mLocation;
    private RequestQueue mRequestQueue;
    private Gson mGson;
    private List<Place> mPlaces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInitialContent();
    }

    private void setInitialContent() {
        getCurrentLocation();
        mRequestQueue = Volley.newRequestQueue(this);
        getData();
    }

    private void getData() {
        String temp = buildURI();
        Log.i("URI", temp);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, buildURI(), null,
                onDataLoaded, OnError);
        mRequestQueue.add(request);
    }

    private final Response.Listener<JSONObject> onDataLoaded = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray results = response.getJSONArray("results");
                mPlaces = new ArrayList<>();
                for (int counter = 0; counter < results.length(); counter++) {
                    JSONObject jsonObj = results.getJSONObject(counter);
                    Place place = new Place(jsonObj.getString("name"),
                            jsonObj.optDouble("rating"),
                            jsonObj.optInt("price_level"),
                            jsonObj.optString("place_id"));
                    mPlaces.add(place);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private final Response.ErrorListener OnError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };

    private String buildURI() {
        String latitude = Double.toString(mLocation.getLatitude());
        String longitude = Double.toString(mLocation.getLongitude());

        return Uri.parse(URL)
                .buildUpon()
                .appendQueryParameter("location", latitude + "," + longitude)
                .appendQueryParameter("radius", "500")
                .appendQueryParameter("type", "coffee")
                .appendQueryParameter("key", API_KEY)
                .build()
                .toString();
    }

    private void getCurrentLocation() {
        boolean isGPSEnabled;

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPSEnabled) {
            //TODO error message
        } else {
            if (isGPSEnabled) {
                if (ContextCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                        .PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                }
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 600000,
                        5000, this);
                if (mLocationManager != null) {
                    mLocation = mLocationManager.getLastKnownLocation(mLocationManager.GPS_PROVIDER);
                }
            }
        }


    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
