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
import com.hyep.movietracker.Listeners.LoadTagsCallback;
import com.hyep.movietracker.Listeners.OnTagClickListener;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.BottomSheetTagAdapter;
import com.hyep.movietracker.helper.FirestoreHelper;
import com.hyep.movietracker.models.TagModel;
import com.hyep.movietracker.screens.CreateTagScreen;

import java.util.ArrayList;

public class BottomSheetTagDetailMovie extends BottomSheetDialogFragment implements OnTagClickListener {
    ImageButton btnCreateTag;
    private FirestoreHelper firestoreHelper;
    private ArrayList<TagModel> tagModelArrayList = new ArrayList<>();
    BottomSheetTagAdapter bottomSheetTagAdapter;
    private static final String ARG_MOVIE_ID = "movie_id";
    private int movieId;

    public BottomSheetTagDetailMovie() {

    }
    public static BottomSheetTagDetailMovie newInstance(int movieId) {
        BottomSheetTagDetailMovie fragment = new BottomSheetTagDetailMovie();
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
        View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet_tags_detail_movie, null);
        bottomSheetDialog.setContentView(view);
        firestoreHelper = new FirestoreHelper(getContext());

        btnCreateTag = view.findViewById(R.id.btnCreateTag);
        btnCreateTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateTagScreen.class);
                intent.putExtra("movieId", movieId);
                startActivity(intent);
            }
        });
        RecyclerView rvTag = view.findViewById(R.id.rvListTag);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvTag.setLayoutManager(linearLayoutManager);
        bottomSheetTagAdapter = new BottomSheetTagAdapter(getContext(), tagModelArrayList, this);
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

    @Override
    public void onTagClicked(String id) {
        firestoreHelper.addMovieToTag(id, String.valueOf(movieId));
    }
}
