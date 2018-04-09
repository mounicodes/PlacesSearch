package com.example.placessearch;

import android.app.VoiceInteractor;
import android.content.Context;
import android.media.MediaPlayer.OnCompletionListener;
import android.telecom.Call;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import org.json.JSONObject;

/**
 * Created by mounicachikkam on 4/7/18.
 */

public class VolleyPlacesFetcher extends PlacesFetcher {

  private Context mContext;
  private static final String TAG = "VolleyPlacesFetcher";
  private RequestQueue mRequestQueue;


  public VolleyPlacesFetcher(Context context) {
    mContext = context;
  }

  @Override
  void fetchData(String uri, final onResultObtainedListener listener) {
    mRequestQueue = Volley.newRequestQueue(mContext);
    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
        uri,
        null,
        new Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            listener.onResultObtained(response);
          }
        },
        new ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "Volley call Error");
          }
        });
    mRequestQueue.add(request);
  }
}
