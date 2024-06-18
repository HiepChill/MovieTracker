package com.hyep.movietracker.screens;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hyep.movietracker.Listeners.LoadSpacesCallback;
import com.hyep.movietracker.helper.FirestoreHelper;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.CastDetailMovieAdapter;
import com.hyep.movietracker.adapter.MediaDetailMovieAdapter;
import com.hyep.movietracker.adapter.RecommendationDetailMovieAdapter;
import com.hyep.movietracker.api.APIClient;
import com.hyep.movietracker.models.BackdropsModel;
import com.hyep.movietracker.models.CastModel;
import com.hyep.movietracker.models.CastResponse;
import com.hyep.movietracker.models.MediaModel;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.MovieResponse;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.models.TrailerModel;
import com.hyep.movietracker.models.TrailerResponse;
import com.hyep.movietracker.screens.fragment.BottomSheetSpaceDetailMovie;
import com.hyep.movietracker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieScreen extends AppCompatActivity {

    private RecyclerView rvCast, rvMedia, rvRecommendation;

    private List<CastModel> listCast;

    private Movie movie;

    private List<Movie> listMovieRecommendation;

    private List<BackdropsModel> medias;

    private ImageButton imgBtnBack, imgBtnAddToSpace, imgBtnAddToWatched, imgBtnAddTag;

    private TextView tvIMDBScore,tvMovieTitle,tvMovieGenres, tvMovieTags, tvInformation;

    private ImageView ivPoster;

    private String idTrailer;

    private WebView wvTrailer;
    private FirestoreHelper firestoreHelper;
    private ArrayList<PersonalSpaceModel> personalSpaceModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_detail_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();

        int movieId = intent.getIntExtra("movieId",0);

        //text view
        tvIMDBScore = findViewById(R.id.tvIMDBScore);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvInformation = findViewById(R.id.tvInformation);
        tvMovieGenres = findViewById(R.id.tvMovieGenres);

        //image view
        ivPoster = findViewById(R.id.ivPoster);


        //button
        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnAddToSpace = findViewById(R.id.imgBtnAddToSpace);
        imgBtnAddToWatched = findViewById(R.id.imgBtnAddToWatched);
        imgBtnAddTag = findViewById(R.id.imgBtnAddTag);


        //recycle view
        rvCast = findViewById(R.id.rvCast);
        GridLayoutManager castLayout = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        rvCast.setLayoutManager(castLayout);


        rvMedia = findViewById(R.id.rvMedia);
        LinearLayoutManager rvMediaLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvMedia.setLayoutManager(rvMediaLayout);

        rvRecommendation = findViewById(R.id.rvRecommendation);
        LinearLayoutManager rvRecommendationLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvRecommendation.setLayoutManager(rvRecommendationLayout);

        //webview
        wvTrailer = findViewById(R.id.wvTrailer);

        //firestorehelper
        firestoreHelper = new FirestoreHelper(this);


        //onclick Button
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgBtnAddToSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String spaceId = "7ca002b0-0acc-4748-8091-ebd0c3129d2b";
//                firestoreHelper.addMovieToSpace(spaceId, String.valueOf(movieId));
                showListSpace();
            }
        });

        // call api
        callDetailMovieById(movieId);
        callListCastById(movieId);
        callMediaById(movieId);
        callRecommendationById(movieId);
        callTrailerById(movieId);
    }

    private void showListSpace() {
        BottomSheetSpaceDetailMovie bottomSheetSpaceDetailMovie = new BottomSheetSpaceDetailMovie();
        bottomSheetSpaceDetailMovie.show(getSupportFragmentManager(), bottomSheetSpaceDetailMovie.getTag());
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpPersonalSpaceArrayList();
    }

    private void setUpPersonalSpaceArrayList() {
        firestoreHelper.loadSpaces(new LoadSpacesCallback() {
            @Override
            public void onLoaded(ArrayList<PersonalSpaceModel> spaces) {
                personalSpaceModelArrayList.clear();
                personalSpaceModelArrayList.addAll(spaces);

            }
        });
    }

    private void callDetailMovieById(int movieId){
        APIClient.getApiInterface().getDetailMovieById(movieId, Utils.API_KEY, Utils.LANGUAGE_ENGLISH).enqueue(new Callback<Movie>() {

            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();

                tvIMDBScore.setText(Math.round(movie.getVoteAverage()*10)+"%");
                tvMovieTitle.setText(movie.getTitle());
                Glide.with(getApplicationContext()).load(Utils.BASE_IMG_URL + movie.getPosterPath()).into(ivPoster);
                tvInformation.setText(movie.getOverview());
                String genres = "";
                for (int i =0 ; i< movie.getGenres().size();i++){
                    genres+=movie.getGenres().get(i).getName() +", ";
                }
                genres = genres.substring(0,genres.length()-2);

                tvMovieGenres.setText(genres);

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callListCastById(int movieId){
        APIClient.getApiInterface().getListCastById(movieId, Utils.API_KEY).enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                assert response.body() != null;
                listCast = response.body().getListCast();


                CastDetailMovieAdapter castDetailMovieAdapter = new CastDetailMovieAdapter(listCast, DetailMovieScreen.this);
                rvCast.setAdapter(castDetailMovieAdapter);
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMediaById(int movieID){
        APIClient.getApiInterface().getMediaById(movieID, Utils.API_KEY).enqueue(new Callback<MediaModel>() {
            @Override
            public void onResponse(Call<MediaModel> call, Response<MediaModel> response) {
                assert response.body() != null;
                medias = response.body().getBackdrops();
                MediaDetailMovieAdapter mediaDetailMovieAdapter = new MediaDetailMovieAdapter(medias,DetailMovieScreen.this);
                rvMedia.setAdapter(mediaDetailMovieAdapter);

            }

            @Override
            public void onFailure(Call<MediaModel> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callRecommendationById(int movieId){
        APIClient.getApiInterface().getRecommendationById(movieId, Utils.API_KEY, Utils.LANGUAGE_ENGLISH).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                assert response.body() != null;
                listMovieRecommendation = response.body().getMovies();

                RecommendationDetailMovieAdapter recommendationDetailMovieAdapter = new RecommendationDetailMovieAdapter(DetailMovieScreen.this, listMovieRecommendation);
                rvRecommendation.setAdapter(recommendationDetailMovieAdapter);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {

            }
        });
    }

    private void callTrailerById(int movieId){
        APIClient.getApiInterface().getTrailerById(movieId,Utils.API_KEY).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                idTrailer = response.body().getListTrailer().get(0).getKey();
                Toast.makeText(DetailMovieScreen.this, idTrailer, Toast.LENGTH_SHORT).show();
                WebSettings webSettings = wvTrailer.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setLoadWithOverviewMode(true);
                webSettings.setUseWideViewPort(true);

                // Đặt WebViewClient để mở URL trong WebView thay vì trình duyệt mặc định
                wvTrailer.setWebViewClient(new WebViewClient());

                // Tải IFrame Player API

                String html ="<html><body style=\"margin:0;padding:0;overflow:hidden;\"><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + idTrailer + "?modestbranding=1&showinfo=0&rel=0&controls=1&disablekb=1&fs=0&iv_load_policy=3\" frameborder=\"0\" allowfullscreen></iframe></body></html>";


                wvTrailer.loadData(html, "text/html", "utf-8");
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable throwable) {

            }
        });
    }
}