package com.hyep.movietracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hyep.movietracker.R;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.screens.DetailMovieScreen;
import com.hyep.movietracker.utils.Utils;

import java.util.List;

public class RecommendationDetailMovieAdapter extends RecyclerView.Adapter<RecommendationDetailMovieAdapter.RecommedationDetailMovieHolder> {

    private final Context cont;

    private final List<Movie> listMovie;

    public RecommendationDetailMovieAdapter(Context cont, List<Movie> listMovie) {
        this.cont = cont;
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public RecommedationDetailMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recommend, parent, false);
        return new RecommedationDetailMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommedationDetailMovieHolder holder, int position) {
        Movie movie = listMovie.get(position);
        if (movie == null){
            return;
        }

        Glide.with(cont).load(Utils.BASE_IMG_URL+movie.getPosterPath()).into(holder.ivPoster);
        holder.tvMovieTitle.setText(movie.getTitle());
        holder.ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cont, DetailMovieScreen.class);
                intent.putExtra("movieId", movie.getId());
                cont.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listMovie != null){
            return listMovie.size();
        }
        return 0;
    }

    public static class RecommedationDetailMovieHolder extends RecyclerView.ViewHolder{


        private final ImageView ivPoster;
        private final TextView tvMovieTitle;

        public RecommedationDetailMovieHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
        }
    }

}
