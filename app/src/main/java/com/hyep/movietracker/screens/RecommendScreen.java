package com.hyep.movietracker.screens;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.DiscoverMovieAdapter;
import com.hyep.movietracker.adapter.DiscoverTVAdapter;
import com.hyep.movietracker.api.APIClient;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.MovieResponse;
import com.hyep.movietracker.models.TV;
import com.hyep.movietracker.models.TVResponse;
import com.hyep.movietracker.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendScreen extends AppCompatActivity {

    private RecyclerView rcvMovie;
    private RecyclerView rcvTV;
    private List<Movie> movies;
    private List<TV> tvList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_recommend);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rcvMovie = findViewById(R.id.rcvMovie);
        rcvTV = findViewById(R.id.rcvTV);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        rcvMovie.setLayoutManager(linearLayoutManager1);
        rcvTV.setLayoutManager(linearLayoutManager2);

        progressDialog = new ProgressDialog(this);

        callMovieApi();
        callTVApi();
    }


    private void callMovieApi() {
        progressDialog.show();

        APIClient.getApiInterface().getDiscoverMovie(Utils.API_KEY, Utils.LANGUAGE_ENGLISH).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                movies = response.body().getMovies();
                DiscoverMovieAdapter movieAdapter = new DiscoverMovieAdapter(movies, getApplicationContext());
                rcvMovie.setAdapter(movieAdapter);
                Log.d("data", movies.toString());
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callTVApi() {
        progressDialog.show();

        APIClient.getApiInterface().getDiscoverTV(Utils.API_KEY, Utils.LANGUAGE_ENGLISH).enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                progressDialog.dismiss();
                tvList = response.body().getTvList();
                DiscoverTVAdapter tvAdapter = new DiscoverTVAdapter(tvList, getApplicationContext());
                rcvTV.setAdapter(tvAdapter);
                Log.d("data", tvList.toString());
            }
            @Override
            public void onFailure(Call<TVResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}