package com.hyep.movietracker.screens.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.PersonalSpaceAdapter;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.screens.UpcomingScreen;
import com.hyep.movietracker.utils.UniqueId;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    ArrayList<PersonalSpaceModel> personalSpaceModelArrayList = new ArrayList<>();

    ImageView imvSpace;
    TextView tvSpace, tvCreate;
    RecyclerView rcvPersonalSpace;
    PersonalSpaceAdapter personalSpaceAdapter;
    ImageButton btnUpComing, btnWatched;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvSpace = view.findViewById(R.id.tvSpace);
        tvCreate = view.findViewById(R.id.tvCreate);
        imvSpace = view.findViewById(R.id.imvSpace);
        rcvPersonalSpace = view.findViewById(R.id.rcvPersonalSpace);
        btnUpComing = view.findViewById(R.id.imgBtnUpcoming);


        btnUpComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpcomingScreen.class);
                startActivity(intent);
            }
        });

        setUpPersonalSpaceArrayList();

        personalSpaceAdapter = new PersonalSpaceAdapter(view.getContext(), personalSpaceModelArrayList);
        rcvPersonalSpace.setAdapter(personalSpaceAdapter);
        rcvPersonalSpace.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        if (!personalSpaceModelArrayList.isEmpty()) {
            tvCreate.setVisibility(View.GONE);
            tvSpace.setVisibility(View.GONE);
            imvSpace.setVisibility(View.GONE);
            rcvPersonalSpace.setVisibility(View.VISIBLE);
        }
        else {
            tvCreate.setVisibility(View.VISIBLE);
            tvSpace.setVisibility(View.VISIBLE);
            imvSpace.setVisibility(View.VISIBLE);
            rcvPersonalSpace.setVisibility(View.GONE);
        }

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                personalSpaceAdapter.deleteItem(position);
                showUndoSnackbar();
                if (!personalSpaceModelArrayList.isEmpty()) {
                    tvCreate.setVisibility(View.GONE);
                    tvSpace.setVisibility(View.GONE);
                    imvSpace.setVisibility(View.GONE);
                    rcvPersonalSpace.setVisibility(View.VISIBLE);
                }
                else {
                    tvCreate.setVisibility(View.VISIBLE);
                    tvSpace.setVisibility(View.VISIBLE);
                    imvSpace.setVisibility(View.VISIBLE);
                    rcvPersonalSpace.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                Drawable icon;
                ColorDrawable background;

                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 20; // For padding around the delete icon

                icon = ContextCompat.getDrawable(view.getContext(), R.drawable.ic_trash); // Set delete icon
                background = new ColorDrawable(ContextCompat.getColor(view.getContext(), R.color.background));

                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();

                int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                int iconRight = itemView.getRight() - iconMargin;
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                        itemView.getTop(), itemView.getRight(), itemView.getBottom());

                background.draw(c);
                icon.draw(c);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rcvPersonalSpace);

        return view;
    }

    private void setUpPersonalSpaceArrayList() {
        PersonalSpaceModel[] personalSpaceModels = {
                new PersonalSpaceModel(UniqueId.generate(),"Phim cua Duong", 6,  0, 0),
                new PersonalSpaceModel(UniqueId.generate(),"Phim cua Hiep", 7,  1, 0),
                new PersonalSpaceModel(UniqueId.generate(),"Phim cua Thanh", 8,  2, 0),
                new PersonalSpaceModel(UniqueId.generate(),"Phim cua Thuy", 9,  3, 0),
                new PersonalSpaceModel(UniqueId.generate(),"Phim cua Duong", 6,  4, 1),
                new PersonalSpaceModel(UniqueId.generate(),"Phim cua Hiep", 7,  5, 1),
                new PersonalSpaceModel(UniqueId.generate(),"Phim cua Thanh", 8,  6, 1),
                new PersonalSpaceModel(UniqueId.generate(),"Phim cua Thuy", 9,  7, 1),
        };

        personalSpaceModelArrayList.addAll(Arrays.asList(personalSpaceModels));
    }

    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(getView(), "Personal space deleted", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalSpaceAdapter.undoDelete();
            }
        });
        snackbar.show();
    }
}