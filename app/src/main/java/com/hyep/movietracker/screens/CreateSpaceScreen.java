package com.hyep.movietracker.screens;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hyep.movietracker.R;

public class CreateSpaceScreen extends AppCompatActivity {

    private EditText edtTag;
    private ImageView[] iconCases;
    private Button btnDone;
    private RelativeLayout case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15;

    private int selectedColor = Color.BLACK; // Default color for EditText and icons
    private ImageView selectedIcon; // Biến để lưu trữ icon được chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_create_space);

        // Initialize views
        edtTag = findViewById(R.id.tag_name);
        btnDone = findViewById(R.id.btnDone);

        case1 = findViewById(R.id.royalBlue);
        case2 = findViewById(R.id.purple);
        case3 = findViewById(R.id.magenta);
        case4 = findViewById(R.id.aquaGreen);
        case5 = findViewById(R.id.chromeYellow);
        case6 = findViewById(R.id.bluePurple);
        case7 = findViewById(R.id.blazeOrange);
        case8 = findViewById(R.id.red);
        case9 = findViewById(R.id.claret);
        case10 = findViewById(R.id.smokeyGrey);
        case11 = findViewById(R.id.purpleJam);
        case12 = findViewById(R.id.brown);
        case13 = findViewById(R.id.green);
        case14 = findViewById(R.id.cobaltBlue);
        case15 = findViewById(R.id.skyBlue);

        // Initialize array to hold icon views
        iconCases = new ImageView[6];
        iconCases[0] = findViewById(R.id.icon1);
        iconCases[1] = findViewById(R.id.icon2);
        iconCases[2] = findViewById(R.id.icon3);
        iconCases[3] = findViewById(R.id.icon4);
        iconCases[4] = findViewById(R.id.icon5);
        iconCases[5] = findViewById(R.id.icon6);

        setIconClickListeners();
        setColorClickListeners();
    }

    private void setIconClickListeners() {
        // Set event listener for each icon
        for (final ImageView iconCase : iconCases) {
            iconCase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Set the selected icon
                    selectedIcon = iconCase;
                    // Update color for the selected icon
                    updateColors();
                }
            });
        }
    }


    private void setColorClickListeners() {
        // Set event listener for color selection
        case1.setOnClickListener(getColorClickListener(R.color.royalBlue));
        case2.setOnClickListener(getColorClickListener(R.color.purple));
        case3.setOnClickListener(getColorClickListener(R.color.magenta));
        case4.setOnClickListener(getColorClickListener(R.color.aquaGreen));
        case5.setOnClickListener(getColorClickListener(R.color.chromeYellow));
        case6.setOnClickListener(getColorClickListener(R.color.bluePurple));
        case7.setOnClickListener(getColorClickListener(R.color.blazeOrange));
        case8.setOnClickListener(getColorClickListener(R.color.red));
        case9.setOnClickListener(getColorClickListener(R.color.claret));
        case10.setOnClickListener(getColorClickListener(R.color.smokeyGrey));
        case11.setOnClickListener(getColorClickListener(R.color.purpleJam));
        case12.setOnClickListener(getColorClickListener(R.color.brown));
        case13.setOnClickListener(getColorClickListener(R.color.green));
        case14.setOnClickListener(getColorClickListener(R.color.cobaltBlue));
        case15.setOnClickListener(getColorClickListener(R.color.skyBlue));
    }

    private View.OnClickListener getColorClickListener(final int colorResId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get color from resources and update selected color
                selectedColor = ContextCompat.getColor(CreateSpaceScreen.this, colorResId);
                // Call updateColors() to update colors for both tag and icon
                updateColors();
            }
        };
    }

    private void updateColors() {
        // Update color for EditText
        edtTag.setTextColor(selectedColor);
        // Update color for the selected icon
        if (selectedIcon != null) {
            selectedIcon.clearColorFilter();
            selectedIcon.getDrawable().setColorFilter(selectedColor, PorterDuff.Mode.SRC_IN);
        }
    }
}
