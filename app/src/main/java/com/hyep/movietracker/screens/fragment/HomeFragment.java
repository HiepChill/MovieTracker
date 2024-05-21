package com.hyep.movietracker.screens.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.PersonalSpaceAdapter;
import com.hyep.movietracker.models.PersonalSpaceModel;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    ArrayList<PersonalSpaceModel> personalSpaceModelArrayList = new ArrayList<>();

    int[] personalSpaceIcons = {
            R.drawable.ic_space_logo_1,
            R.drawable.ic_space_logo_2,
            R.drawable.ic_space_logo_3,
            R.drawable.ic_space_logo_4,
            R.drawable.ic_space_logo_5,
            R.drawable.ic_space_logo_6,
    };

    ImageView imvSpace;
    TextView tvSpace, tvCreate;
    RecyclerView rcvPersonalSpace;
    PersonalSpaceAdapter personalSpaceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvSpace = view.findViewById(R.id.tvSpace);
        tvCreate = view.findViewById(R.id.tvCreate);
        imvSpace = view.findViewById(R.id.imvSpace);
        rcvPersonalSpace = view.findViewById(R.id.rcvPersonalSpace);

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

        return view;
    }

    private void setUpPersonalSpaceArrayList() {
        PersonalSpaceModel[] personalSpaceModels = {
                new PersonalSpaceModel("Phim cua Duong", 6,  0, 0),
                new PersonalSpaceModel("Phim cua Hiep", 7,  1, 0),
                new PersonalSpaceModel("Phim cua Thanh", 8,  2, 0),
                new PersonalSpaceModel("Phim cua Thuy", 9,  3, 0),
                new PersonalSpaceModel("Phim cua Duong", 6,  4, 1),
                new PersonalSpaceModel("Phim cua Hiep", 7,  5, 1),
                new PersonalSpaceModel("Phim cua Thanh", 8,  6, 1),
                new PersonalSpaceModel("Phim cua Thuy", 9,  7, 1),
        };

        personalSpaceModelArrayList.addAll(Arrays.asList(personalSpaceModels));
    }
}