package com.hyep.movietracker.screens.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hyep.movietracker.R;
import com.hyep.movietracker.screens.DetailSpaceScreen;

public class BottomSheetSettingSpaceFragment extends BottomSheetDialogFragment {

    private Button btnEditSpace, btnDeleteSpace;

    private Context cont = getContext();
    public BottomSheetSettingSpaceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cont = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_setting_space, container, false);

        btnEditSpace = view.findViewById(R.id.btnEditSpace);
        btnDeleteSpace = view.findViewById(R.id.btnDeleteSpace);

        btnEditSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(cont, "Edit Space", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(cont, "Delete Space", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}