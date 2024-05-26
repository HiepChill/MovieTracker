package com.hyep.movietracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.hyep.movietracker.R;
import com.hyep.movietracker.models.BackdropsModel;
import com.hyep.movietracker.utils.Utils;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MediaDetailMovieAdapter extends RecyclerView.Adapter<MediaDetailMovieAdapter.MediaDetailMovieHolder> {

    private final List<BackdropsModel> medias;

    private final Context cont;

    public MediaDetailMovieAdapter(List<BackdropsModel> medias, Context cont) {
        this.medias = medias;
        this.cont = cont;
    }

    @NonNull
    @Override
    public MediaDetailMovieAdapter.MediaDetailMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_media, parent, false);
        return new MediaDetailMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaDetailMovieAdapter.MediaDetailMovieHolder holder, int position) {
        BackdropsModel media = medias.get(position);

        if (media == null) {
            return;
        }

        Glide.with(cont).load(Utils.BASE_IMG_URL + media.getFilePath()).into(holder.ivMedia);
    }

    @Override
    public int getItemCount() {
        if (medias != null) {
            return medias.size();
        }
        return 0;
    }

    public static class MediaDetailMovieHolder extends RecyclerView.ViewHolder{
        private final ImageView ivMedia;

        public MediaDetailMovieHolder(@NonNull View itemView) {
            super(itemView);

            ivMedia = itemView.findViewById(R.id.ivMedia);

        }
    }
}
