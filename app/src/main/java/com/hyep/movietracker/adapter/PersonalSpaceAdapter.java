package com.hyep.movietracker.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.R;
import com.hyep.movietracker.models.PersonalSpaceModel;

import java.util.ArrayList;

public class PersonalSpaceAdapter extends RecyclerView.Adapter<PersonalSpaceAdapter.MyViewHolder>{

    Context context;
    ArrayList<PersonalSpaceModel> personalSpaceModelArrayList;
    PersonalSpaceModel recentlyDeletedItem;
    int recentlyDeletedItemPosition;

    public PersonalSpaceAdapter(Context context, ArrayList<PersonalSpaceModel> personalSpaceModelArrayList) {
        this.context = context;
        this.personalSpaceModelArrayList = personalSpaceModelArrayList;
    }

    @NonNull
    @Override
    public PersonalSpaceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_personal_space, parent, false);
        return new PersonalSpaceAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalSpaceAdapter.MyViewHolder holder, int position) {
        int[] personalSpaceIcons = {
                R.drawable.ic_space_logo_1,
                R.drawable.ic_space_logo_2,
                R.drawable.ic_space_logo_3,
                R.drawable.ic_space_logo_4,
                R.drawable.ic_space_logo_5,
                R.drawable.ic_space_logo_6,
        };

        int[] personalSpaceColors = {
                R.color.royalBlue,
                R.color.purple,
                R.color.magenta,
                R.color.aquaGreen,
                R.color.chromeYellow,
                R.color.bluePurple,
                R.color.blazeOrange,
                R.color.red,
                R.color.claret,
                R.color.smokeyGrey,
                R.color.purpleJam,
                R.color.brown,
                R.color.green,
                R.color.cobaltBlue,
                R.color.skyBlue,
        };

        holder.tvPersonalSpaceName.setText(personalSpaceModelArrayList.get(position).getName());
        holder.tvPersonalSpaceNumber.setText(personalSpaceModelArrayList.get(position).getNumber() + " Movies");
        holder.imvPersonalSpaceIcon.setImageResource(personalSpaceIcons[personalSpaceModelArrayList.get(position).getIcon()]);
        int color = ContextCompat.getColor(holder.itemView.getContext(), personalSpaceColors[personalSpaceModelArrayList.get(position).getColor()]);
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        ViewCompat.setBackgroundTintList(holder.itemView, colorStateList);
    }

    @Override
    public int getItemCount() {
        return personalSpaceModelArrayList.size();
    }

    public void deleteItem(int position) {
        PersonalSpaceModel item = personalSpaceModelArrayList.get(position);
        personalSpaceModelArrayList.remove(position);
        notifyItemRemoved(position);
        recentlyDeletedItem = item;
        recentlyDeletedItemPosition = position;
    }

    public void undoDelete() {
        personalSpaceModelArrayList.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //similar to onCreate method

        TextView tvPersonalSpaceName, tvPersonalSpaceNumber;
        ImageView imvPersonalSpaceIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPersonalSpaceName = itemView.findViewById(R.id.tvPersonalSpaceName);
            tvPersonalSpaceNumber = itemView.findViewById(R.id.tvPersonalSpaceNumber);
            imvPersonalSpaceIcon = itemView.findViewById(R.id.imvPersonalSpaceIcon);
        }
    }
}