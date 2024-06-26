package com.hyep.movietracker.screens;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hyep.movietracker.R;
import com.hyep.movietracker.screens.fragment.HomeFragment;
import com.hyep.movietracker.screens.fragment.SettingFragment;
import com.hyep.movietracker.screens.fragment.StaticFragment;
import com.hyep.movietracker.screens.fragment.TagFragment;

public class MainScreen extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabAdd;
    View popupCreateNew;
    ImageButton imgBtnCreateSpace, imgBtnCreateTag;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth =  FirebaseAuth.getInstance();

        // Ensure that the content view fits the system windows
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            // Handle insets here if needed
            return insets.consumeSystemWindowInsets();
        });

        frameLayout = findViewById(R.id.fragmentContainer);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        replaceFragment(new HomeFragment());

        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menuItemHome) {
                replaceFragment(new HomeFragment());
                return true;
            }
            if (item.getItemId() == R.id.menuItemTag) {
                replaceFragment(new TagFragment());
                return true;
            }
            if (item.getItemId() == R.id.menuItemStatic) {
                replaceFragment(new StaticFragment());
                return true;
            }
            if (item.getItemId() == R.id.menuItemSetting) {
                replaceFragment(new SettingFragment());
                return true;
            }
            return false;
        });

        fabAdd = findViewById(R.id.fabAdd);
        popupCreateNew = findViewById(R.id.popupCreateNew);

        imgBtnCreateSpace = findViewById(R.id.imgBtnCreateSpace);
        imgBtnCreateTag = findViewById(R.id.imgBtnCreateTag);

        imgBtnCreateSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, CreateSpaceScreen.class);
                intent.putExtra("mode", "create");
                startActivity(intent);
            }
        });

        imgBtnCreateTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, CreateTagScreen.class);
                startActivity(intent);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    public void toggleFABandPopup(View view) {
        if (popupCreateNew.getVisibility() == View.GONE) {
            fabAdd.setImageResource(R.drawable.ic_cancel);
            fabAdd.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            popupCreateNew.setVisibility(View.VISIBLE);
        } else {
            fabAdd.setImageResource(R.drawable.ic_add);
            fabAdd.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.chromeYellow)));
            popupCreateNew.setVisibility(View.GONE);
        }
    }


}