package com.hyep.movietracker.models;

public class WatchedMovie {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_MOVIE = 1;

    private int type;

    private String header;

    private Movie movie;

    public WatchedMovie(int type, String header) {
        this.type = type;
        this.header = header;
    }

    public WatchedMovie(int type, Movie movie) {
        this.type = type;
        this.movie = movie;
    }

    public int getType() {
        return type;
    }

    public String getHeader() {
        return header;
    }

    public Movie getMovie() {
        return movie;
    }
}
