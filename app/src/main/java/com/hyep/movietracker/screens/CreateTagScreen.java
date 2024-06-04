package com.hyep.movietracker.screens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hyep.movietracker.R;

public class CreateTagScreen extends AppCompatActivity {
    private EditText edtTag;
    private ImageView icon_enter_tag;
    private Button btnDone;
    private RelativeLayout case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15;
    private int selectedColor = Color.BLACK; // Default color for EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_create_tag);

        icon_enter_tag = findViewById(R.id.icon_enter_tag);
        btnDone = findViewById(R.id.btnDone);
        edtTag = findViewById(R.id.tag_name);
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

        View camelCase = findViewById(R.id.main);
        camelCase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

        // Set click listeners for color options
        setOnClickListeners();


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Apply the selected color to EditText
                edtTag.setTextColor(selectedColor);

                // Perform other actions after the "Done" button is pressed
                // For example, close the screen
//                finish();
            }
        });

        icon_enter_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTagScreen.this, CreateSpaceScreen.class);
                startActivity(intent);
            }
        });
    }

    private void setOnClickListeners() {
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
                selectedColor = ContextCompat.getColor(CreateTagScreen.this, colorResId);
                edtTag.setTextColor(selectedColor);
            }
        };
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
