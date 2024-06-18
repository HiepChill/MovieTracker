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
import com.hyep.movietracker.Listeners.LoadTagsCallback;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.BottomSheetTagAdapter;
import com.hyep.movietracker.helper.FirestoreHelper;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.models.TagModel;

import java.util.ArrayList;

public class BottomSheetTagDetailMovie extends BottomSheetDialogFragment {
    private FirestoreHelper firestoreHelper;
    private ArrayList<TagModel> tagModelArrayList = new ArrayList<>();
    BottomSheetTagAdapter bottomSheetTagAdapter;


    public BottomSheetTagDetailMovie() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet_tags_detail_movie, null);
        bottomSheetDialog.setContentView(view);
        firestoreHelper = new FirestoreHelper(getContext());

        RecyclerView rvTag = view.findViewById(R.id.rvListTag);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvTag.setLayoutManager(linearLayoutManager);
        bottomSheetTagAdapter = new BottomSheetTagAdapter(getContext(), tagModelArrayList);
        rvTag.setAdapter(bottomSheetTagAdapter);


        return bottomSheetDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTagArrayList();
    }

    private void loadTagArrayList() {
        firestoreHelper.loadTags(new LoadTagsCallback() {
            @Override
            public void onLoaded(ArrayList<TagModel> tags) {
                tagModelArrayList.clear();
                tagModelArrayList.addAll(tags);
                bottomSheetTagAdapter.notifyDataSetChanged();

            }
        });
    }
}
