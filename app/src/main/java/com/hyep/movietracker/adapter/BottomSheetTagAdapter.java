package com.hyep.movietracker.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.R;

import com.hyep.movietracker.models.TagModel;
import com.hyep.movietracker.utils.Utils;

import java.util.ArrayList;

public class BottomSheetTagAdapter extends RecyclerView.Adapter<BottomSheetTagAdapter.TagViewHolder>{

    Context context;
    ArrayList<TagModel> tagModelArrayList;


    public BottomSheetTagAdapter(Context context, ArrayList<TagModel> tagModelArrayList) {
        this.context = context;
        this.tagModelArrayList = tagModelArrayList;
    }
    @NonNull
    @Override
    public BottomSheetTagAdapter.TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_tag_bts, parent, false);
        return new BottomSheetTagAdapter.TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomSheetTagAdapter.TagViewHolder holder, int position) {
        holder.tvTagName.setText("#" + tagModelArrayList.get(position).getName());
        int color = ContextCompat.getColor(holder.itemView.getContext(), Utils.listColors[tagModelArrayList.get(position).getColor()]);
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        ViewCompat.setBackgroundTintList(holder.itemView, colorStateList);
        String id = tagModelArrayList.get(position).getId();

    }

    @Override
    public int getItemCount() {
        return tagModelArrayList.size();
    }


    public static class TagViewHolder extends RecyclerView.ViewHolder{

        TextView tvTagName;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTagName = itemView.findViewById(R.id.tvTagName);

        }
    }
}
