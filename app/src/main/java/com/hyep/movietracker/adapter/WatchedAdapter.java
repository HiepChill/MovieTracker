package com.hyep.movietracker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
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
import com.google.type.Color;
import com.hyep.movietracker.R;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.WatchedMovie;
import com.hyep.movietracker.utils.Utils;

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
//            headerViewHolder.headerTextView.setText(header);
            setFormattedHeader(headerViewHolder.headerTextView, header);
        }else if(holder instanceof WatchedHolder){
            WatchedHolder watchedHolder = (WatchedHolder) holder;
            Movie movie = item.getMovie();
            watchedHolder.tvGenre.setText("Movie");
            watchedHolder.tvDate.setText(movie.getReleaseDate().toString());
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
    private void setFormattedHeader(TextView textView, String header) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        Date parsedDate;
        try {
            parsedDate = sdf.parse(header);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Định dạng phần ngày trong chuỗi header
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        String day = dayFormat.format(parsedDate);

        // Tạo một SpannableString để định dạng văn bản
        SpannableString spannableString = new SpannableString(header);

        // Đặt màu sắc cho phần ngày (đầu chuỗi)
        int color = ContextCompat.getColor(cont, R.color.white);
        spannableString.setSpan(new ForegroundColorSpan(color), 0, day.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Đặt kích thước chữ cho phần ngày (đầu chuỗi)
        spannableString.setSpan(new RelativeSizeSpan(1.5f), 0, day.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Đặt spannableString vào TextView
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
