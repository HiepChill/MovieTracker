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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.CardViewAdapter;
import com.hyep.movietracker.api.APIClient;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.MovieResponse;
import com.hyep.movietracker.utils.Utils;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpcomingScreen extends AppCompatActivity {

    private RecyclerView rcvMovieCard;
    private List<Movie> movies;
    private ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_upcoming);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        rcvMovieCard = findViewById(R.id.rcvMovieCard);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvMovieCard.setLayoutManager(linearLayoutManager);


        movies = new ArrayList<>();
        progressDialog = new ProgressDialog(this);

        callMovieApi();


    }

    private void callMovieApi() {
        progressDialog.show();

        APIClient.getApiInterface().getUpcomingMovies(Utils.API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                movies = response.body().getMovies();
                CardViewAdapter cardViewAdapter = new CardViewAdapter(movies, getApplicationContext());
                rcvMovieCard.setAdapter(cardViewAdapter);

                Log.d("data", movies.toString());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }


}