package com.hyep.movietracker.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hyep.movietracker.Listeners.OnItemClickListener;
import com.hyep.movietracker.R;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.utils.Utils;

import java.util.List;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder> {
    private final List<Movie> movieList;
    private Context con;
    OnItemClickListener listener;

    String dateTimeFormat = "MMM dd, yyyy";

    public SearchMovieAdapter(List<Movie> movieList, Context con, OnItemClickListener listener) {
        this.movieList = movieList;
        this.con = con;
        this.listener = listener;
    }

    public SearchMovieAdapter(List<Movie> movieList, Context con) {
        this.movieList = movieList;
        this.con = con;
    }

    @NonNull
    @Override
    public SearchMovieAdapter.SearchMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new SearchMovieAdapter.SearchMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieAdapter.SearchMovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        if (movie == null) {
            return;
        }
        Glide.with(con).load(Utils.BASE_IMG_URL + movieList.get(position).getPosterPath()).into(holder.imgPoster);
        holder.tvTitle.setText(movieList.get(position).getTitle());
        holder.tvGenre.setText("Movie");
        holder.tvReleaseDate.setText(String.valueOf(DateFormat.format(dateTimeFormat, movieList.get(position).getReleaseDate())));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMovieClicked(movie.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }


    public static class SearchMovieViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvReleaseDate;
        private  TextView tvGenre;
        private ImageView imgPoster;
        private CardView container;

        public SearchMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvReleaseDate = itemView.findViewById(R.id.tvDate);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            imgPoster = itemView.findViewById(R.id.imagePoster);
            container = itemView.findViewById(R.id.cvLayout);
        }
    }
}
