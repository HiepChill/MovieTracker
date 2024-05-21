package com.hyep.movietracker.screens;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
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
import com.hyep.movietracker.adapter.DiscoverMovieAdapter;
import com.hyep.movietracker.adapter.DiscoverTVAdapter;
import com.hyep.movietracker.adapter.SearchMovieAdapter;
import com.hyep.movietracker.api.APIClient;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.MovieResponse;
import com.hyep.movietracker.models.SearchModel;
import com.hyep.movietracker.models.TV;
import com.hyep.movietracker.models.TVResponse;
import com.hyep.movietracker.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchScreen extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView rcvMovie;
    private RecyclerView rcvTV;
    private RecyclerView rcvSearch;
    private List<Movie> movies, list;
    private List<SearchModel> results;
    private List<TV> tvList;
    private EditText edtSearch;
    private TextView txtMovie, txtTV;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtSearch = findViewById(R.id.edtSearch);
        txtMovie = findViewById(R.id.txtMovie);
        txtTV = findViewById(R.id.txtTV);

        rcvSearch = findViewById(R.id.rcvSearchView);
        rcvMovie = findViewById(R.id.rcvMovie);
        rcvTV = findViewById(R.id.rcvTV);


        setLayout(rcvSearch);
        setLayout(rcvMovie);
        setLayout(rcvTV);

        progressDialog = new ProgressDialog(this);

        callMovieApi();
        callTVApi();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edtSearch.getText().toString().equals("")) {

                    String query = edtSearch.getText().toString();

                    searchMovie(Utils.API_KEY, query, Utils.LANGUAGE_ENGLISH);
                }
            }
        });

//        edtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (!edtSearch.getText().toString().equals("")) {
//                    rcvMovie.setVisibility(View.GONE);
//                    String query = edtSearch.getText().toString();
//
//                    searchMovie(Utils.API_KEY, query, Utils.LANGUAGE_ENGLISH);
//                }
//            }
//        });

    }

    private void setLayout(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    private void callMovieApi() {
        progressDialog.show();

        APIClient.getApiInterface().getDiscoverMovie(Utils.API_KEY, Utils.LANGUAGE_ENGLISH).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                movies = response.body().getMovies();
                DiscoverMovieAdapter movieAdapter = new DiscoverMovieAdapter(movies, getApplicationContext(), SearchScreen.this::onMovieClicked);
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
                DiscoverTVAdapter tvAdapter = new DiscoverTVAdapter(tvList, getApplicationContext(), SearchScreen.this::onMovieClicked);
                rcvTV.setAdapter(tvAdapter);
                Log.d("data", tvList.toString());
            }
            @Override
            public void onFailure(Call<TVResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMovie(String api_key, String query, String language) {
        APIClient.getApiInterface().getListMovieBySearch(Utils.API_KEY, query, Utils.LANGUAGE_ENGLISH).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                list = response.body().getMovies();
                SearchMovieAdapter searchAdapter = new SearchMovieAdapter(list, getApplicationContext(), SearchScreen.this::onMovieClicked);
                rcvSearch.setAdapter(searchAdapter);
                Log.d("data", movies.toString());
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Can not find", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMovieClicked(int id) {
        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
    }
}