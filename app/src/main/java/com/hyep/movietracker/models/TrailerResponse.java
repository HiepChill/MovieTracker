package com.hyep.movietracker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("results")
    @Expose
    private List<TrailerModel> listTrailer;

    public TrailerResponse(String id, List<TrailerModel> listTrailer) {
        this.id = id;
        this.listTrailer = listTrailer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TrailerModel> getListTrailer() {
        return listTrailer;
    }

    public void setListTrailer(List<TrailerModel> listTrailer) {
        this.listTrailer = listTrailer;
    }
}
