package com.hyep.movietracker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hyep.movietracker.models.TV;

import java.util.List;

public class TVResponse {
    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private List<TV> tvList = null;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TV> getTvList() {
        return tvList;
    }

    public void setTvList(List<TV> tvList) {
        this.tvList = tvList;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
