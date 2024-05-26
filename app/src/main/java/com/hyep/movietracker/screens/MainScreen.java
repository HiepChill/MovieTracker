package com.hyep.movietracker.screens;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hyep.movietracker.R;
import com.hyep.movietracker.screens.fragment.HomeFragment;
import com.hyep.movietracker.screens.fragment.SettingFragment;
import com.hyep.movietracker.screens.fragment.StaticFragment;
import com.hyep.movietracker.screens.fragment.TagFragment;

public class MainScreen extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

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

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}