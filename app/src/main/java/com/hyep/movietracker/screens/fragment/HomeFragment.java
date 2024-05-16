package com.hyep.movietracker.screens.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyep.movietracker.R;
import com.hyep.movietracker.models.PersonalSpace;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<PersonalSpace> personalSpaceList = new ArrayList<>();

    PersonalSpace personalSpace1 = new PersonalSpace("Movies of Zwng", 10, 1, "chromeYellow");

    ImageView imvSpace;
    TextView tvSpace, tvCreate;
    RecyclerView rcvPersonalSpace;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvSpace = view.findViewById(R.id.tvSpace);
        tvCreate = view.findViewById(R.id.tvCreate);
        imvSpace = view.findViewById(R.id.imvSpace);

        personalSpaceList.add(personalSpace1);

        if (!personalSpaceList.isEmpty()) {
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

        return view;
    }
}