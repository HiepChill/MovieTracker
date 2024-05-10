package com.hyep.movietracker.screens;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.CardViewAdapter;
import com.hyep.movietracker.api.ApiService;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingScreen extends AppCompatActivity {

    private RecyclerView rcvMovieCard;
    private List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upcoming_screen);

        rcvMovieCard = findViewById(R.id.rcvMovieCard);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvMovieCard.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvMovieCard.addItemDecoration(itemDecoration);

        movies = new ArrayList<>();
        callMovieApi();
    }

    private void callMovieApi() {
        ApiService.apiService.getUpcomingMovies("0b9dffdf2cc5ef177909e12da7782207").enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movies = (List<Movie>) response.body();
                CardViewAdapter cardViewAdapter = new CardViewAdapter(movies);
                rcvMovieCard.setAdapter(cardViewAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}