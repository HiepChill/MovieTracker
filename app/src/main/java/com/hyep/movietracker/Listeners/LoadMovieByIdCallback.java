package com.hyep.movietracker.Listeners;

import com.hyep.movietracker.models.Movie;

public interface LoadMovieByIdCallback {
    void onLoaded(Movie movie);
}
