package com.hyep.movietracker.screens.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hyep.movietracker.R;
import com.hyep.movietracker.screens.LoginScreen;
import com.hyep.movietracker.screens.NewPasswordScreen;

public class SettingFragment extends Fragment {

    private ImageView imvProfile;

    private TextView tvName;

    private AppCompatButton btnChangePassword, btnLogOut;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);

        imvProfile = view.findViewById(R.id.imvProfile);
        tvName = view.findViewById(R.id.tvName);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnLogOut = view.findViewById(R.id.btnLogOut);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user!=null){
            String name = user.getDisplayName();
            Uri uri = user.getPhotoUrl();
            String email = user.getEmail();

            tvName.setText(name);
            imvProfile.setImageURI(uri);

        }

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewPasswordScreen.class));
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return view;
    }
}