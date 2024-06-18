package com.hyep.movietracker.screens.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hyep.movietracker.Listeners.LoadSpacesCallback;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.BottomSheetSpaceAdapter;
import com.hyep.movietracker.helper.FirestoreHelper;
import com.hyep.movietracker.models.PersonalSpaceModel;

import java.util.ArrayList;

public class BottomSheetSpaceDetailMovie extends BottomSheetDialogFragment {

    private FirestoreHelper firestoreHelper;
    private ArrayList<PersonalSpaceModel> personalSpaceModelArrayList = new ArrayList<>();
    BottomSheetSpaceAdapter bottomSheetSpaceAdapter;


    public BottomSheetSpaceDetailMovie() {

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet_detail_movie, null);
        bottomSheetDialog.setContentView(view);
        firestoreHelper = new FirestoreHelper(getContext());

        RecyclerView rvSpace = view.findViewById(R.id.rvListSpace);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvSpace.setLayoutManager(linearLayoutManager);
        bottomSheetSpaceAdapter = new BottomSheetSpaceAdapter(getContext(), personalSpaceModelArrayList);
        rvSpace.setAdapter(bottomSheetSpaceAdapter);


        return bottomSheetDialog;
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
                bottomSheetSpaceAdapter.notifyDataSetChanged();

            }
        });
    }

}
