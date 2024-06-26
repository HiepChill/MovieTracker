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

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.hyep.movietracker.Listeners.DeleteSpaceCallback;
import com.hyep.movietracker.Listeners.LoadSpacesCallback;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.PersonalSpaceAdapter;
import com.hyep.movietracker.helper.FirestoreHelper;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.screens.DetailSpaceScreen;
import com.hyep.movietracker.screens.UpcomingScreen;
import com.hyep.movietracker.screens.WatchedScreen;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<PersonalSpaceModel> personalSpaceModelArrayList = new ArrayList<>();
    private ImageView imvSpace;
    private TextView tvSpace, tvCreate;
    private RecyclerView rcvPersonalSpace;
    private PersonalSpaceAdapter personalSpaceAdapter;
    private ImageButton btnUpComing, btnWatched;
    private FirestoreHelper firestoreHelper;
    private Handler handler;
    private Runnable deleteRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvSpace = view.findViewById(R.id.tvSpace);
        tvCreate = view.findViewById(R.id.tvCreate);
        imvSpace = view.findViewById(R.id.imvSpace);
        rcvPersonalSpace = view.findViewById(R.id.rcvPersonalSpace);
        btnUpComing = view.findViewById(R.id.imgBtnUpcoming);
        btnWatched = view.findViewById(R.id.imgBtnWatched);

        firestoreHelper = new FirestoreHelper(view.getContext());
        handler = new Handler();

        btnUpComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpcomingScreen.class);
                startActivity(intent);
            }
        });

        btnWatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WatchedScreen.class);
                startActivity(intent);
            }
        });

        personalSpaceAdapter = new PersonalSpaceAdapter(view.getContext(), personalSpaceModelArrayList);

        personalSpaceAdapter.setOnItemClickListener(position -> {
            PersonalSpaceModel space = personalSpaceModelArrayList.get(position);

            Intent intent = new Intent(view.getContext(), DetailSpaceScreen.class);
            intent.putExtra("id", space.getId());
            intent.putExtra("size", space.getSize());
            intent.putExtra("name", space.getName());
            intent.putExtra("color", space.getColor());
            intent.putExtra("icon", space.getIcon());
            startActivity(intent);
        });

        rcvPersonalSpace.setAdapter(personalSpaceAdapter);
        rcvPersonalSpace.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                PersonalSpaceModel deletedSpace = personalSpaceModelArrayList.get(position);
                personalSpaceAdapter.deleteItem(position);
                showUndoSnackbar(deletedSpace, position);
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
        itemTouchHelper.attachToRecyclerView(rcvPersonalSpace);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpPersonalSpaceArrayList();
    }

    private void setUpPersonalSpaceArrayList() {
        firestoreHelper.loadSpaces(new LoadSpacesCallback() {
            @Override
            public void onLoaded(ArrayList<PersonalSpaceModel> spaces) {
                personalSpaceModelArrayList.clear();
                personalSpaceModelArrayList.addAll(spaces);
                personalSpaceAdapter.notifyDataSetChanged();
                updateEmptyViewVisibility();
            }
        });
    }

    private void showUndoSnackbar(PersonalSpaceModel deletedSpace, int position) {
        Snackbar snackbar = Snackbar.make(getView(), "Personal space deleted", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(deleteRunnable);
                personalSpaceAdapter.undoDelete();
                updateEmptyViewVisibility();
            }
        });
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                    deleteRunnable = new Runnable() {
                        @Override
                        public void run() {
                            firestoreHelper.deleteSpace(deletedSpace.getId(), new DeleteSpaceCallback() {
                                @Override
                                public void onDeleted() {
                                    setUpPersonalSpaceArrayList();
                                }
                            });
                        }
                    };
                    handler.postDelayed(deleteRunnable, Snackbar.LENGTH_LONG);
                }
            }
        });
        snackbar.show();
    }

    private void updateEmptyViewVisibility() {
        if (!personalSpaceModelArrayList.isEmpty()) {
            tvCreate.setVisibility(View.GONE);
            tvSpace.setVisibility(View.GONE);
            imvSpace.setVisibility(View.GONE);
            rcvPersonalSpace.setVisibility(View.VISIBLE);
        } else {
            tvCreate.setVisibility(View.VISIBLE);
            tvSpace.setVisibility(View.VISIBLE);
            imvSpace.setVisibility(View.VISIBLE);
            rcvPersonalSpace.setVisibility(View.GONE);
        }
    }
}