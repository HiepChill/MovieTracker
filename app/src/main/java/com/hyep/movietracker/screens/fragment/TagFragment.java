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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hyep.movietracker.Listeners.LoadTagsCallback;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.TagAdapter;
import com.hyep.movietracker.helper.FirestoreHelper;
import com.hyep.movietracker.models.TagModel;
import com.hyep.movietracker.screens.DetailTagScreen;
import com.hyep.movietracker.utils.UniqueId;

import java.util.ArrayList;
import java.util.Arrays;

public class TagFragment extends Fragment {

    private ArrayList<TagModel> tagModelArrayList = new ArrayList<>();
    private ImageView imvTag;
    private TextView tvNoTag, tvCreateTag;
    private RecyclerView rcvTag;
    private TagAdapter tagAdapter;
    private FirestoreHelper firestoreHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tag, container, false);

        imvTag = view.findViewById(R.id.imvTag);
        tvNoTag = view.findViewById(R.id.tvNoTag);
        tvCreateTag = view.findViewById(R.id.tvCreateTag);
        rcvTag = view.findViewById(R.id.rcvTag);

        firestoreHelper = new FirestoreHelper(view.getContext());

        tagAdapter = new TagAdapter(view.getContext(), tagModelArrayList);
        tagAdapter.setOnItemClickListener(position -> {
            TagModel clickedTag = tagModelArrayList.get(position);

            Intent intent = new Intent(getActivity(), DetailTagScreen.class);
            intent.putExtra("id", clickedTag.getId());
            intent.putExtra("name",clickedTag.getName());
            intent.putExtra("color", clickedTag.getColor());
            startActivity(intent);
        });


        rcvTag.setAdapter(tagAdapter);
        rcvTag.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

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
                updateEmptyViewVisibility();
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

    @Override
    public void onResume() {
        super.onResume();
        setUpTagModelArrayList();
    }

    private void setUpTagModelArrayList() {
        firestoreHelper.loadTags(new LoadTagsCallback() {
            @Override
            public void onLoaded(ArrayList<TagModel> tags) {
                tagModelArrayList.clear();
                tagModelArrayList.addAll(tags);
                tagAdapter.notifyDataSetChanged();
                updateEmptyViewVisibility();
            }
        });
    }

    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(rcvTag, "Tag deleted", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagAdapter.undoDelete();
                updateEmptyViewVisibility();
            }
        });
        snackbar.show();
    }

    private void updateEmptyViewVisibility() {
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
}