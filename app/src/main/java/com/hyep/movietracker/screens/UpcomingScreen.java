package com.hyep.movietracker.screens;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingSource;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.CardViewAdapter;
import com.hyep.movietracker.adapter.MovieAdapter;
import com.hyep.movietracker.api.APIClient;
import com.hyep.movietracker.api.ApiService;
import com.hyep.movietracker.databinding.ActivityMainBinding;
import com.hyep.movietracker.databinding.ActivityUpcomingScreenBinding;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.MovieResponse;
import com.hyep.movietracker.paging.MoviePagingSource;
import com.hyep.movietracker.utils.GridSpace;
import com.hyep.movietracker.utils.MovieComparator;
import com.hyep.movietracker.utils.Utils;
import com.hyep.movietracker.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class UpcomingScreen extends AppCompatActivity {

    private RecyclerView rcvMovieCard;
    private List<Movie> movies;
    private ProgressDialog progressDialog;

    private MoviePagingSource moviePagingSource;

//    MovieViewModel upcomingViewModel;
//    ActivityUpcomingScreenBinding binding;
//    MovieAdapter movieAdapter;

//    @Inject
//    RequestManager requestManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityUpcomingScreenBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upcoming_screen);


        rcvMovieCard = findViewById(R.id.rcvMovieCard);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvMovieCard.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvMovieCard.addItemDecoration(itemDecoration);

        movies = new ArrayList<>();
        progressDialog = new ProgressDialog(this);

        callMovieApi();
    }

    private void callMovieApi() {
        progressDialog.show();
        ApiService.apiService.getUpcomingMovies(Utils.API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();
                movies = response.body().getMovies();
                CardViewAdapter cardViewAdapter = new CardViewAdapter(movies);
                rcvMovieCard.setAdapter(cardViewAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });



    }


    //        if (Utils.API_KEY == null || Utils.API_KEY.isEmpty()) {
//            Toast.makeText(this, "API_KEY ERROR", Toast.LENGTH_SHORT).show();
//        }
//
//        movieAdapter = new MovieAdapter(new MovieComparator(), requestManager);
//
//        upcomingViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
//
//        initRecyclerviewAndAdapter();
//
//        //subcribe to paging data
//        upcomingViewModel.moviePagingDataFlowable.subscribe(moviePagingData -> {
//            movieAdapter.submitData(getLifecycle(), moviePagingData);
//        });
//    }
//
//    private void initRecyclerviewAndAdapter() {
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
//
//        binding.rcvMovieCard.setLayoutManager(gridLayoutManager);
//
//        binding.rcvMovieCard.addItemDecoration(new GridSpace(2, 20, true));
//
//
//
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return movieAdapter.getItemViewType(position) == MovieAdapter.LOADING_ITEM ? 1 : 2;
//            }
//        });

}