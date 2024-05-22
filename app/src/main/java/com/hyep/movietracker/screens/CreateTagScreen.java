package com.hyep.movietracker.screens;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hyep.movietracker.R;

public class CreateTagScreen extends AppCompatActivity {
    private EditText edtTag;
    private RelativeLayout case1, case2, case3, case4, case5, case6, case7, case8, case9, case10, case11, case12, case13, case14, case15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_create_tag);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtTag = findViewById(R.id.tag_name);
        case1 = findViewById(R.id.mau5);
        case2 = findViewById(R.id.mau4);
        case3 = findViewById(R.id.mau1);
        case4 = findViewById(R.id.mau2);
        case5 = findViewById(R.id.mau3);
        case6 = findViewById(R.id.mau10);
        case7 = findViewById(R.id.mau9);
        case8 = findViewById(R.id.mau6);
        case9 = findViewById(R.id.mau7);
        case10 = findViewById(R.id.mau8);
        case11 = findViewById(R.id.mau15);
        case12 = findViewById(R.id.mau14);
        case13 = findViewById(R.id.mau11);
        case14 = findViewById(R.id.mau12);
        case15 = findViewById(R.id.mau13);

        View camelCase = findViewById(R.id.camelCase);
        camelCase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

        // Set click listeners for color options
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        case1.setOnClickListener(getColorClickListener(R.color.skyBlue));
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
                edtTag.setTextColor(ContextCompat.getColor(CreateTagScreen.this, colorResId));
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
