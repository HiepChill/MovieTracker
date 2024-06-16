package com.hyep.movietracker.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.R;
import com.hyep.movietracker.models.TagModel;
import com.hyep.movietracker.utils.Utils;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {

    Context context;
    ArrayList<TagModel> tagModelArrayList;
    TagModel recentlyDeletedItem;
    int recentlyDeletedItemPosition;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TagAdapter(Context context, ArrayList<TagModel> tagModelArrayList) {
        this.context = context;
        this.tagModelArrayList = tagModelArrayList;
    }

    @NonNull

    @Override
    public TagAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_tag, parent, false);
        return new TagAdapter.MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.MyViewHolder holder, int position) {

        int color = ContextCompat.getColor(holder.itemView.getContext(), Utils.listColors[tagModelArrayList.get(position).getColor()]);
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        ViewCompat.setBackgroundTintList(holder.itemView, colorStateList);

        holder.tvTagName.setText("#" + tagModelArrayList.get(position).getName());
        holder.tvTagName.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return tagModelArrayList.size();
    }

    public void deleteItem(int position) {
        recentlyDeletedItem = tagModelArrayList.get(position);
        recentlyDeletedItemPosition = position;
        tagModelArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void undoDelete() {
        tagModelArrayList.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTagName;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            tvTagName = itemView.findViewById(R.id.tvTagName);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
