package com.hyep.movietracker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Movie {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("release_date")
    @Expose
    private Date releaseDate;

    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("genres")
    @Expose
    private List<GenresModel> genres;

    private Date watchedDate;

    public Movie(int id, String posterPath, String title) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
    }

    public Movie(int id, String posterPath, String title, Date releaseDate, Date watchedDate){
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.watchedDate = watchedDate;
    }

    public Movie(int id, String posterPath, String title, Date releaseDate, Float voteAverage, String overview, List<GenresModel> genres) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.genres = genres;
        this.watchedDate = null;
    }

    public List<GenresModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresModel> genres) {
        this.genres = genres;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public Date getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(Date watchedDate) {
        this.watchedDate = watchedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
