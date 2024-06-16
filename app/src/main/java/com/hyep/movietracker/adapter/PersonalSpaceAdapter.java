package com.hyep.movietracker.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.hyep.movietracker.utils.Utils;
import com.hyep.movietracker.screens.DetailSpaceScreen;

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

        holder.tvPersonalSpaceName.setText(personalSpaceModelArrayList.get(position).getName());
        holder.tvPersonalSpaceNumber.setText(personalSpaceModelArrayList.get(position).getSize() + " Movies");
        holder.imvPersonalSpaceIcon.setImageResource(Utils.listIcons[personalSpaceModelArrayList.get(position).getIcon()]);
        int color = ContextCompat.getColor(holder.itemView.getContext(), Utils.listColors[personalSpaceModelArrayList.get(position).getColor()]);
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        ViewCompat.setBackgroundTintList(holder.itemView, colorStateList);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalSpaceModel clickedSpace = personalSpaceModelArrayList.get(position);
                Intent intent = new Intent(context, DetailSpaceScreen.class);
                intent.putExtra("name", clickedSpace.getName());
                intent.putExtra("size", clickedSpace.getSize());
                intent.putExtra("icon", clickedSpace.getIcon());
                intent.putExtra("color", clickedSpace.getColor());
                context.startActivity(intent);
            }
        });
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