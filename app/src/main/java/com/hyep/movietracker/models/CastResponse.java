package com.hyep.movietracker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("cast")
    @Expose
    private List<CastModel> listCast;

    public CastResponse(int id, List<CastModel> listCast) {
        this.id = id;
        this.listCast = listCast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CastModel> getListCast() {
        return listCast;
    }

    public void setListCast(List<CastModel> listCast) {
        this.listCast = listCast;
    }
}
