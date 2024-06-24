package com.hyep.movietracker.screens;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.WatchedAdapter;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.WatchedMovie;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WatchedScreen extends AppCompatActivity {

    private ImageButton imgBtnBack;

    private RecyclerView rcvWatched;
    private WatchedAdapter watchedAdapter;
    private List<Movie> movieList;

    private ArrayList<WatchedMovie> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_watched);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgBtnBack = findViewById(R.id.imgBtnBack);
        rcvWatched = findViewById(R.id.rcvWatched);

        imgBtnBack.setOnClickListener(v->{
            finish();
        });


        movieList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            movieList.add(new Movie(1022789, "/oxxqiyWrnM0XPnBtVe9TgYWnPxT.jpg", "Inside Out 2",
                    dateFormat.parse("2024-06-06"), dateFormat.parse("2024-06-11")));
            movieList.add(new Movie(1086747, "/vZVEUPychdvZLrTNwWErr9xZFmu.jpg", "The Watchers",
                    dateFormat.parse("2024-06-06"), dateFormat.parse("2024-06-11")));
            movieList.add(new Movie(748783, "/tSPsiMHb4edeBKZZjKDmhX18Jbs.jpg", "Inside Out 2",
                    dateFormat.parse("2024-06-11"), dateFormat.parse("2024-03-11")));
            movieList.add(new Movie(1022789, "/oxxqiyWrnM0XPnBtVe9TgYWnPxT.jpg", "Inside Out 2",
                    dateFormat.parse("2024-06-11"), dateFormat.parse("2024-03-11")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        itemList = prepareItems(movieList);
        rcvWatched.setLayoutManager(new LinearLayoutManager(this));
        watchedAdapter = new WatchedAdapter(this, itemList, new WatchedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(WatchedScreen.this, "aaaaa", Toast.LENGTH_SHORT).show();
            }
        });
        rcvWatched.setAdapter(watchedAdapter);
    }

    private ArrayList<WatchedMovie> prepareItems(List<Movie> movies){
        ArrayList<WatchedMovie> items = new ArrayList<>();
        if (movies == null || movies.isEmpty()){
            return items;
        }

        movies.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie movie, Movie t1) {
                return movie.getWatchedDate().compareTo(t1.getWatchedDate());
            }
        });



        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");

        String lastHeader = "";
        for (Movie movie: movies){
            String hear = sdf.format(movie.getWatchedDate());
            if (!hear.equals(lastHeader)){
                items.add(new WatchedMovie(WatchedMovie.TYPE_HEADER, hear));
                lastHeader = hear;
            }
            items.add(new WatchedMovie(WatchedMovie.TYPE_MOVIE, movie));
        }
        return items;
    }
}