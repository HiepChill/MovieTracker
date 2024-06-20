package com.hyep.movietracker.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hyep.movietracker.R;
import com.hyep.movietracker.helper.FirestoreHelper;
import com.hyep.movietracker.models.TagModel;
import com.hyep.movietracker.utils.UniqueId;
import com.hyep.movietracker.utils.Utils;

public class CreateTagScreen extends AppCompatActivity {
    private EditText edtTagName;
    private ImageView imvArrowBack;
    private Button btnDone;
    private RelativeLayout[] colorCases;
    private int selectedColor = 0;
    private FirestoreHelper firestoreHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_create_tag);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imvArrowBack = findViewById(R.id.imvArrowBack);
        btnDone = findViewById(R.id.btnDone);
        edtTagName = findViewById(R.id.edtTagName);

        colorCases = new RelativeLayout[15];
        colorCases[0] = findViewById(R.id.royalBlue);
        colorCases[1] = findViewById(R.id.purple);
        colorCases[2] = findViewById(R.id.magenta);
        colorCases[3] = findViewById(R.id.aquaGreen);
        colorCases[4] = findViewById(R.id.chromeYellow);
        colorCases[5] = findViewById(R.id.bluePurple);
        colorCases[6] = findViewById(R.id.blazeOrange);
        colorCases[7] = findViewById(R.id.red);
        colorCases[8] = findViewById(R.id.claret);
        colorCases[9] = findViewById(R.id.smokeyGrey);
        colorCases[10] = findViewById(R.id.purpleJam);
        colorCases[11] = findViewById(R.id.brown);
        colorCases[12] = findViewById(R.id.green);
        colorCases[13] = findViewById(R.id.cobaltBlue);
        colorCases[14] = findViewById(R.id.skyBlue);

        Utils.setHideKeyboardOnTouch(this, findViewById(R.id.main));
        setColorClickListeners();

        firestoreHelper = new FirestoreHelper(this);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagModel tag = new TagModel(
                        UniqueId.generate(),
                        edtTagName.getText().toString(),
                        0,
                        selectedColor
                );
                firestoreHelper.createTag(tag);
                finish();
            }
        });

        imvArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setColorClickListeners() {
        for (int i = 0; i < colorCases.length; i++) {
            int index = i;
            colorCases[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedColor = index;
                    int color = ContextCompat.getColor(CreateTagScreen.this, Utils.listColors[selectedColor]);
                    edtTagName.setTextColor(color);
                }
            });
        }
    }
}
