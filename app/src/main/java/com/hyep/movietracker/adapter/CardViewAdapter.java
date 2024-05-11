package com.hyep.movietracker.adapter;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.hyep.movietracker.R;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.MovieResponse;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    public static final int LOADING_ITEM = 0;
    public static final int MOVIE_ITEM = 1;
    RequestManager glide;
    private final List<Movie> movieList;

    String dateTimeFormat = "MMM dd, yyyy";
    public CardViewAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public CardViewAdapter(RequestManager glide, List<Movie> movieList) {
        this.glide = glide;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_upcoming_movies, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        if (movie == null) {
            return;
        }
//        glide.load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
//                .into(holder.imgPoster);
        holder.tvGenre.setText("Movie");
        holder.tvTitle.setText(String.valueOf(movie.getTitle()));
        holder.tvReleaseDate.setText(String.valueOf(DateFormat.format(dateTimeFormat, movie.getReleaseDate())));
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvReleaseDate;
        private  TextView tvGenre;
        private ImageView imgPoster;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvReleaseDate = itemView.findViewById(R.id.tvDate);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            imgPoster = itemView.findViewById(R.id.imgPoster);
        }
    }
}
