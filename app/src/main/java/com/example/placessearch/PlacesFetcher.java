package com.example.placessearch;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Abstract class to get data from Places API and parse it
 */
public abstract class PlacesFetcher {

  private final static String TAG = "PlacesFetcher";
  private final static String NEARBY_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
  private final static String PLACE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?";
  private static Location mLocation;

  /**
   * Build Uri to get data from Places API
   */
  public static String buildPlacesUri(Context context, Activity activity, String apiKey,
      String radius) {

    mLocation = getCurrentLocation(context, activity);
    return Uri.parse(NEARBY_SEARCH_URL)
        .buildUpon()
        .appendQueryParameter("location",
            mLocation.getLatitude() + "," + mLocation.getLongitude())
        .appendQueryParameter("radius", radius)
        .appendQueryParameter("type", "coffee")
        .appendQueryParameter("key", apiKey)
        .build()
        .toString();
  }

  /**
   * Gets current location, used in the method to build places uri
   */
  private static Location getCurrentLocation(Context context, Activity activity) {

    LocationListener locationListener = new LocationListener() {
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
    };
    boolean isGPSEnabled;
    LocationManager locationManager;
    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    if (!isGPSEnabled) {
      Toast.makeText(context, "GPS is disabled", Toast.LENGTH_LONG).show();
      return null;
    }
    if (ContextCompat.checkSelfPermission(context, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(activity, new String[]{permission.ACCESS_FINE_LOCATION}, 1);
      return null;
    }
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 600000,
        5000, locationListener);
    if (locationManager != null) {
      return locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
    }
    return null;
  }

  /**
   * Network call to be implemented.
   */
  abstract void fetchData(String uri, onResultObtainedListener listener);

  /**
   * Json parser
   *
   * @return returns an empty list if nothing is found or else returns list of places
   */
  public static List<Place> parseJson(JSONObject data) {
    List<Place> places = new ArrayList<>();
    try {
      JSONArray results = data.getJSONArray("results");
      JSONArray photos;
      String photoReference = null;
      for (int counter = 0; counter < results.length(); counter++) {
        JSONObject jsonObj = results.getJSONObject(counter);
        String uri = null;
        if (!jsonObj.isNull("photos")) {
          photos = jsonObj.getJSONArray("photos");
          photoReference = ((JSONObject) photos.get(0)).getString
              ("photo_reference");
          uri = Uri.parse(PLACE_PHOTO_URL)
              .buildUpon()
              .appendQueryParameter("maxwidth", "400")
              .appendQueryParameter("photoreference", photoReference)
              .appendQueryParameter("key", "AIzaSyBBuiXIK0tDJgMUqHOqEyZugoN53_Bc_BY")
              .toString();
        }
        Place place = new Place(jsonObj.getString("name"),
            jsonObj.optDouble("rating"),
            jsonObj.optInt("price_level"),
            jsonObj.optString("place_id"),
            uri);
        places.add(place);
      }
    } catch (JSONException e) {
      Log.e(TAG, e.getMessage(), e);
    }
    return places;
  }

  /**
   * Register the listener and implement the method to get Json response
   */
  public interface onResultObtainedListener {

    void onResultObtained(JSONObject jsonObject);
  }
}
