package com.hyep.movietracker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MediaModel {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("backdrops")
    @Expose
    private List<BackdropsModel> backdrops;

    public MediaModel(int id, List<BackdropsModel> backdrops) {
        this.id = id;
        this.backdrops = backdrops;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<BackdropsModel> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<BackdropsModel> backdrops) {
        this.backdrops = backdrops;
    }
}
