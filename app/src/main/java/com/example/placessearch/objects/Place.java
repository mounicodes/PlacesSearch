package com.example.placessearch.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Object to store place information
 */

public class Place {

  @SerializedName("icon")
  @Expose
  private String icon;
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("place_id")
  @Expose
  private String placeId;
  @SerializedName("reference")
  @Expose
  private String reference;
  @SerializedName("scope")
  @Expose
  private String scope;
  @SerializedName("types")
  @Expose
  private List<String> types = null;
  @SerializedName("vicinity")
  @Expose
  private String vicinity;
  @SerializedName("rating")
  @Expose
  private int rating;
  @SerializedName("opening_hours")
  @Expose
  private OpeningHours openingHours;

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  private String uri;

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Place withIcon(String icon) {
    this.icon = icon;
    return this;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Place withId(String id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Place withName(String name) {
    this.name = name;
    return this;
  }

  public String getPlaceId() {
    return placeId;
  }

  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }

  public Place withPlaceId(String placeId) {
    this.placeId = placeId;
    return this;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public Place withReference(String reference) {
    this.reference = reference;
    return this;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public Place withScope(String scope) {
    this.scope = scope;
    return this;
  }

  public List<String> getTypes() {
    return types;
  }

  public void setTypes(List<String> types) {
    this.types = types;
  }

  public Place withTypes(List<String> types) {
    this.types = types;
    return this;
  }

  public String getVicinity() {
    return vicinity;
  }

  public void setVicinity(String vicinity) {
    this.vicinity = vicinity;
  }

  public Place withVicinity(String vicinity) {
    this.vicinity = vicinity;
    return this;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public Place withRating(int rating) {
    this.rating = rating;
    return this;
  }

  public OpeningHours getOpeningHours() {
    return openingHours;
  }

  public void setOpeningHours(OpeningHours openingHours) {
    this.openingHours = openingHours;
  }

  public Place withOpeningHours(OpeningHours openingHours) {
    this.openingHours = openingHours;
    return this;
  }
}
