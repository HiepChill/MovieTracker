package com.hyep.movietracker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.protobuf.GeneratedMessageLite;
import com.google.type.Color;
import com.hyep.movietracker.R;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.WatchedMovie;
import com.hyep.movietracker.utils.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WatchedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context cont;

    private ArrayList<WatchedMovie> movieArrayList;

    private WatchedMovie recentlyDeletedItem;

    private int recentlyDeletedItemPosition;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public WatchedAdapter(Context cont, ArrayList<WatchedMovie> movieArrayList, OnItemClickListener onItemClickListener) {
        this.cont = cont;
        this.movieArrayList = movieArrayList;
        this.onItemClickListener = onItemClickListener;
    }





    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == WatchedMovie.TYPE_HEADER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_watched, parent, false);
            return new HeaderViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
            return new WatchedHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WatchedMovie item = movieArrayList.get(position);
        if (holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            String header = item.getHeader();
            setFormattedHeader(headerViewHolder.headerTextView, header);
        }else if(holder instanceof WatchedHolder){
            WatchedHolder watchedHolder = (WatchedHolder) holder;
            Movie movie = item.getMovie();
            watchedHolder.tvGenre.setText("Movie");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");

            watchedHolder.tvDate.setText(simpleDateFormat.format(movie.getReleaseDate()));
            watchedHolder.tvTitle.setText(movie.getTitle());
            Glide.with(cont).load(Utils.BASE_IMG_URL + movie.getPosterPath()).into(watchedHolder.imagePoster);

            watchedHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(cont, movie.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return movieArrayList.get(position).getType();
    }

    @SuppressLint("ResourceAsColor")
    private void setFormattedHeader(@NonNull TextView textView, String header) {
        SpannableString spannableString = new SpannableString(header);
        int color = ContextCompat.getColor(cont, R.color.secondary);
        spannableString.setSpan(
                new ForegroundColorSpan(color),
                0,
                2,
                spannableString.SPAN_EXCLUSIVE_INCLUSIVE
        );

        spannableString.setSpan(
                new StyleSpan(Typeface.BOLD),
                0,
                2,
                spannableString.SPAN_EXCLUSIVE_INCLUSIVE
        );

        spannableString.setSpan(
                new RelativeSizeSpan(2f),
                0,
                2,
                spannableString.SPAN_EXCLUSIVE_INCLUSIVE
        );
        textView.setText(spannableString);
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView headerTextView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.headerTextView);
        }
    }

    public static class WatchedHolder extends RecyclerView.ViewHolder{

        private ShapeableImageView imagePoster;

        private TextView tvGenre, tvTitle, tvDate;

        public WatchedHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
