package com.hyep.movietracker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SearchModel {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("release_date")
    @Expose
    private Date releaseDate;

    @SerializedName("media_type")
    @Expose
    private String mediaType;


}
