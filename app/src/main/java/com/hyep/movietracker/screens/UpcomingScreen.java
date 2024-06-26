package com.hyep.movietracker.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.Listeners.OnItemClickListener;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.UpcomingAdapter;
import com.hyep.movietracker.api.APIClient;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.MovieResponse;
import com.hyep.movietracker.utils.Utils;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpcomingScreen extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView rcvMovieCard;
    private List<Movie> movies;
    private ProgressDialog progressDialog;
    private ImageButton imgBtnBack;


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

        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callMovieApi() {
        progressDialog.show();

        APIClient.getApiInterface().getUpcomingMovies(Utils.API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                movies = response.body().getMovies();
                UpcomingAdapter upcomingAdapter = new UpcomingAdapter(movies, getApplicationContext(), UpcomingScreen.this::onMovieClicked);
                rcvMovieCard.setAdapter(upcomingAdapter);

                Log.d("data", movies.toString());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onMovieClicked(int id) {
        Intent intent = new Intent(this, DetailMovieScreen.class);
        intent.putExtra("movieId", id);
        startActivity(intent);
    }
}