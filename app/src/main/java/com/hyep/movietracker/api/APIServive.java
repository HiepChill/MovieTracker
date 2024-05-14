package com.hyep.movietracker.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyep.movietracker.models.MovieResponse;
import com.hyep.movietracker.utils.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServive {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    APIServive apiService = new Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIServive.class);

    @GET("discover/movie")
    Call<MovieResponse> getDiscoverMovie(@Query("api_key") String api_key);
}
