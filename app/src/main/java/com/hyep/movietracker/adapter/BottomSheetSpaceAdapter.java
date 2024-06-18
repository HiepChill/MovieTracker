package com.hyep.movietracker.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.Listeners.OnSpaceClickListener;
import com.hyep.movietracker.R;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.utils.Utils;

import java.util.ArrayList;

public class BottomSheetSpaceAdapter extends RecyclerView.Adapter<BottomSheetSpaceAdapter.SpaceViewHolder>{

    Context context;
    ArrayList<PersonalSpaceModel> personalSpaceModelArrayList;
    OnSpaceClickListener listener;

    public BottomSheetSpaceAdapter(Context context, ArrayList<PersonalSpaceModel> personalSpaceModelArrayList, OnSpaceClickListener listener) {
        this.context = context;
        this.personalSpaceModelArrayList = personalSpaceModelArrayList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public SpaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_space_bts, parent, false);
        return new SpaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpaceViewHolder holder, int position) {
        holder.tvPersonalSpaceName.setText(personalSpaceModelArrayList.get(position).getName());
        holder.imvPersonalSpaceIcon.setImageResource(Utils.listIcons[personalSpaceModelArrayList.get(position).getIcon()]);
        int color = ContextCompat.getColor(holder.itemView.getContext(), Utils.listColors[personalSpaceModelArrayList.get(position).getColor()]);
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        ViewCompat.setBackgroundTintList(holder.itemView, colorStateList);
        String id = personalSpaceModelArrayList.get(position).getId();


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSpaceClicked(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personalSpaceModelArrayList.size();
    }


    public static class SpaceViewHolder extends RecyclerView.ViewHolder{

        TextView tvPersonalSpaceName;
        ImageView imvPersonalSpaceIcon;
        CardView container;

        public SpaceViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPersonalSpaceName = itemView.findViewById(R.id.tvPersonalSpaceName);
            imvPersonalSpaceIcon = itemView.findViewById(R.id.imvPersonalSpaceIcon);
            container = itemView.findViewById(R.id.cvItemSpace);
        }
    }
}
