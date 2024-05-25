package com.hyep.movietracker.screens.fragment;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.TagAdapter;
import com.hyep.movietracker.models.TagModel;

import java.util.ArrayList;
import java.util.Arrays;

public class TagFragment extends Fragment {

    ArrayList<TagModel> tagModelArrayList = new ArrayList<>();

    ImageView imvTag;
    TextView tvNoTag, tvCreateTag;
    RecyclerView rcvTag;
    TagAdapter tagAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tag, container, false);

        imvTag = view.findViewById(R.id.imvTag);
        tvNoTag = view.findViewById(R.id.tvNoTag);
        tvCreateTag = view.findViewById(R.id.tvCreateTag);
        rcvTag = view.findViewById(R.id.rcvTag);

        setUpTagModelArrayList();

        tagAdapter = new TagAdapter(view.getContext(), tagModelArrayList);
        rcvTag.setAdapter(tagAdapter);
        rcvTag.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        if (!tagModelArrayList.isEmpty()) {
            imvTag.setVisibility(View.GONE);
            tvNoTag.setVisibility(View.GONE);
            tvCreateTag.setVisibility(View.GONE);
            rcvTag.setVisibility(View.VISIBLE);
        } else {
            imvTag.setVisibility(View.VISIBLE);
            tvNoTag.setVisibility(View.VISIBLE);
            tvCreateTag.setVisibility(View.VISIBLE);
            rcvTag.setVisibility(View.GONE);
        }

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                tagAdapter.deleteItem(position);
                showUndoSnackbar();
                if (!tagModelArrayList.isEmpty()) {
                    imvTag.setVisibility(View.GONE);
                    tvNoTag.setVisibility(View.GONE);
                    tvCreateTag.setVisibility(View.GONE);
                    rcvTag.setVisibility(View.VISIBLE);
                } else {
                    imvTag.setVisibility(View.VISIBLE);
                    tvNoTag.setVisibility(View.VISIBLE);
                    tvCreateTag.setVisibility(View.VISIBLE);
                    rcvTag.setVisibility(View.GONE);
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
        itemTouchHelper.attachToRecyclerView(rcvTag);

        return view;
    }

    private void setUpTagModelArrayList() {
        TagModel[] tagModels = {
                new TagModel("Action", 0),
                new TagModel("Adventure", 1),
                new TagModel("Animation", 2),
                new TagModel("Comedy", 3),
                new TagModel("Crime", 4),
        };

        tagModelArrayList.addAll(Arrays.asList(tagModels));
    }

    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(rcvTag, "Tag deleted", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", v -> tagAdapter.undoDelete());
        snackbar.show();
    }
}