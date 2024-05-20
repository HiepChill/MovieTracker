package com.hyep.movietracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.R;
import com.hyep.movietracker.models.PersonalSpace;

import java.util.ArrayList;
import java.util.List;

public class PersonalSpaceAdapter extends RecyclerView.Adapter<PersonalSpaceAdapter.MyViewHolder>{

    Context context;
    ArrayList<PersonalSpace> personalSpaceArrayList;

    public PersonalSpaceAdapter(Context context, ArrayList<PersonalSpace> personalSpaceArrayList) {
        this.context = context;
        this.personalSpaceArrayList = personalSpaceArrayList;
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
        holder.tvPersonalSpaceName.setText(personalSpaceArrayList.get(position).getName());
        holder.tvPersonalSpaceNumber.setText(personalSpaceArrayList.get(position).getNumber() + " Movies");
        //holder.imvPersonalSpaceIcon.setImageResource(personalSpaceArrayList.get(position).getIconId());
    }

    @Override
    public int getItemCount() {
        return personalSpaceArrayList.size();
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