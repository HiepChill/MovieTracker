package com.hyep.movietracker.screens.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hyep.movietracker.Listeners.LoadSpacesCallback;
import com.hyep.movietracker.Listeners.OnSpaceClickListener;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.BottomSheetSpaceAdapter;
import com.hyep.movietracker.helper.FirestoreHelper;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.screens.CreateSpaceScreen;

import java.util.ArrayList;

public class BottomSheetSpaceDetailMovie extends BottomSheetDialogFragment implements OnSpaceClickListener {

    private ImageButton btnCreateSpace;
    private FirestoreHelper firestoreHelper;
    private ArrayList<PersonalSpaceModel> personalSpaceModelArrayList = new ArrayList<>();
    BottomSheetSpaceAdapter bottomSheetSpaceAdapter;
    private static final String ARG_MOVIE_ID = "movie_id";
    private int movieId;

    public BottomSheetSpaceDetailMovie() {

    }

    public static BottomSheetSpaceDetailMovie newInstance(int movieId) {
        BottomSheetSpaceDetailMovie fragment = new BottomSheetSpaceDetailMovie();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_ID, String.valueOf(movieId));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = Integer.parseInt(getArguments().getString(ARG_MOVIE_ID));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet_spaces_detail_movie, null);
        bottomSheetDialog.setContentView(view);
        firestoreHelper = new FirestoreHelper(getContext());

        btnCreateSpace = view.findViewById(R.id.btnCreateSpace);
        btnCreateSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateSpaceScreen.class);
                startActivity(intent);
            }
        });
        RecyclerView rvSpace = view.findViewById(R.id.rvListSpace);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvSpace.setLayoutManager(linearLayoutManager);
        bottomSheetSpaceAdapter = new BottomSheetSpaceAdapter(getContext(), personalSpaceModelArrayList, this);
        rvSpace.setAdapter(bottomSheetSpaceAdapter);


        return bottomSheetDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPersonalSpaceArrayList();
    }

    private void loadPersonalSpaceArrayList() {
        firestoreHelper.loadSpaces(new LoadSpacesCallback() {
            @Override
            public void onLoaded(ArrayList<PersonalSpaceModel> spaces) {
                personalSpaceModelArrayList.clear();
                personalSpaceModelArrayList.addAll(spaces);
                bottomSheetSpaceAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onSpaceClicked(String id) {
        firestoreHelper.addMovieToSpace(id, String.valueOf(movieId));
    }
}
