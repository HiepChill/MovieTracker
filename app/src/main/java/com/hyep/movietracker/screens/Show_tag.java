package com.hyep.movietracker.screens;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hyep.movietracker.R;

public class Show_tag extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_tag_screen);

        // Retrieve data from intent
        String tagName = getIntent().getStringExtra("TAG_NAME");
        int iconId = getIntent().getIntExtra("ICON_ID", -1);
        int color = getIntent().getIntExtra("COLOR", Color.BLACK);

        // Set tag name
        TextView txtTagName = findViewById(R.id.txtTagName);
        txtTagName.setText(tagName);

        // Set icon
        ImageView imgIcon = findViewById(R.id.imgIcon);
        if (iconId != -1) {
            imgIcon.setImageResource(iconId);
            imgIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }

    }
}
