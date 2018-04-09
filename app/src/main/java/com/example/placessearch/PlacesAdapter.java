package com.example.placessearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter implementation
 */
public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

  private List<Place> mPlaceList = new ArrayList<>();
  private Context mContext;

  public static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView mName;
    public TextView mPriceLevel;
    public RatingBar mRating;
    public ImageView mImage;

    public ViewHolder(View view) {
      super(view);
      mName = view.findViewById(R.id.place_name);
      mPriceLevel = view.findViewById(R.id.price_level);
      mRating = view.findViewById(R.id.rating);
      mImage = view.findViewById(R.id.image);
    }
  }

  public PlacesAdapter(Context context, List<Place> places) {
    mPlaceList = places;
    mContext = context;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_place, null);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Place place = mPlaceList.get(position);
    holder.mName.setText(place.getName());
    holder.mPriceLevel.setText(String.valueOf(place.getPriceLevel()));
    holder.mRating.setRating((float) place.getRating());
    Glide.with(mContext)
        .load(place.getPhoto())
        .error(R.drawable.ic_not_found)
        .into(holder.mImage);
  }

  @Override
  public int getItemCount() {
    return mPlaceList.size();
  }
}
