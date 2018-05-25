package com.example.placessearch.activities;

import android.Manifest;
import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.placessearch.adapter.PlacesAdapter;
import com.example.placessearch.network.PlacesFetcher.onResultObtainedListener;
import com.example.placessearch.R;
import com.example.placessearch.network.VolleyPlacesFetcher;
import com.example.placessearch.objects.Place;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private List<Place> mPlaces;
  private PlacesAdapter mPlaceArrayAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mPlaces = new ArrayList<>();

    // Initialize recycler view and set data adapter
    // Check if permissions are present and show app compat permission dialog
    if (ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat
          .requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    } else {
      fetchData();
    }

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView
        .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mPlaceArrayAdapter = new PlacesAdapter(this, mPlaces);
    recyclerView.setAdapter(mPlaceArrayAdapter);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == 1) {
      fetchData();
    } else {
      Toast.makeText(this, "Location permission required", Toast.LENGTH_LONG).show();
    }
  }

  private void fetchData() {
    //Fetch Data
    final VolleyPlacesFetcher placesFetcher = new VolleyPlacesFetcher(this);
    String uri = placesFetcher
        .buildPlacesUri(this, this, getResources().getString(R.string.apiKey), "50000");
    onResultObtainedListener listener = new onResultObtainedListener() {
      @Override
      public void onResultObtained(JSONObject jsonObject) {
        mPlaces.addAll(placesFetcher.parseJson(jsonObject));
        mPlaceArrayAdapter.notifyDataSetChanged();
      }
    };
    placesFetcher.fetchData(uri, listener);
  }
}
