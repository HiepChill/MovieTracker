package com.hyep.movietracker.Listeners;

import com.hyep.movietracker.models.Movie;

import java.util.List;

public interface LoadMoviesCallback {
    void onLoaded(List<Movie> movies);
}
