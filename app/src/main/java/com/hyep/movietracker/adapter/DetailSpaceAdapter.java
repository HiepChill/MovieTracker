package com.hyep.movietracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.bumptech.glide.RequestManager;
import com.hyep.movietracker.R;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.screens.DetailMovieScreen;
import com.hyep.movietracker.screens.fragment.BottomSheetSettingItemInSpaceFragment;
import com.hyep.movietracker.utils.Utils;

import java.util.List;

public class DetailSpaceAdapter extends RecyclerView.Adapter<DetailSpaceAdapter.DetailSpaceHolder> {

//    RequestManager glide;

    private final List<Movie> movieList;
    private final Context cont;
    private final FragmentManager fragmentManager;

//    private AdapterView.OnItemClickListener itemClickListener;

    public DetailSpaceAdapter(List<Movie> movieList, Context cont, FragmentManager fragmentManager) {
        this.movieList = movieList;
        this.cont = cont;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public DetailSpaceAdapter.DetailSpaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie_in_space,parent,false);
        return new DetailSpaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailSpaceAdapter.DetailSpaceHolder holder, int position) {
        Movie movie = movieList.get(position);
        if(movie == null){
            return;
        }
        Glide.with(cont).load(Utils.BASE_IMG_URL+movieList.get(position).getPosterPath()).into(holder.imgPoster);
        holder.tvItemTitle.setText(movieList.get(position).getTitle());
        holder.imgBtnItemSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetSettingItemInSpaceFragment bottomSheetSettingItemInSpaceFragment = new BottomSheetSettingItemInSpaceFragment();
                bottomSheetSettingItemInSpaceFragment.show(fragmentManager, bottomSheetSettingItemInSpaceFragment.getTag());
            }
        });
        holder.imgPoster.setOnClickListener(new View.OnClickListener() {
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
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }

    public static class DetailSpaceHolder extends RecyclerView.ViewHolder {

        private final ImageView imgPoster;

        private final TextView tvItemTitle;

        private final ImageButton imgBtnItemSetting;




        public DetailSpaceHolder(View viewItem) {
            super(viewItem);
            imgPoster = viewItem.findViewById(R.id.imgPoster);
            tvItemTitle = viewItem.findViewById(R.id.tvItemTitle);
            imgBtnItemSetting = viewItem.findViewById(R.id.imgBtnItemSetting);



        }
    }


}
