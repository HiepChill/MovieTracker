package com.hyep.movietracker.screens;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hyep.movietracker.R;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.utils.FirestoreHelper;
import com.hyep.movietracker.utils.UniqueId;
import com.hyep.movietracker.utils.Utils;

public class CreateSpaceScreen extends AppCompatActivity {

    private EditText edtSpaceName;
    private ImageView[] iconCases;
    private RelativeLayout[] colorCases;
    private Button btnDone;
    private int selectedColor = 0;
    private int selectedIcon = 0;
    private ImageView selectedIconCase = null;
    private FirestoreHelper firestoreHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_create_space);

        edtSpaceName = findViewById(R.id.edtSpaceName);
        btnDone = findViewById(R.id.btnDone);
        ImageView imvArrowBack = findViewById(R.id.imvArrowBack);

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

        iconCases = new ImageView[6];
        iconCases[0] = findViewById(R.id.icon1);
        iconCases[1] = findViewById(R.id.icon2);
        iconCases[2] = findViewById(R.id.icon3);
        iconCases[3] = findViewById(R.id.icon4);
        iconCases[4] = findViewById(R.id.icon5);
        iconCases[5] = findViewById(R.id.icon6);

        setIconClickListeners();
        setColorClickListeners();
        setDoneButtonClickListener();

        imvArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firestoreHelper = new FirestoreHelper(this);
    }

    private void setDoneButtonClickListener() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalSpaceModel space = new PersonalSpaceModel(
                        UniqueId.generate(),
                        edtSpaceName.getText().toString(),
                        0,
                        selectedColor,
                        selectedIcon
                );
                firestoreHelper.createSpace(space);
            }
        });
    }

    private void setIconClickListeners() {
        for (int i = 0; i < iconCases.length; i++) {
            int index = i;
            iconCases[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedIconCase != null) {
                        selectedIconCase.setSelected(false);
                        selectedIconCase.setColorFilter(Color.WHITE);
                    }

                    selectedIconCase = iconCases[index];
                    selectedIconCase.setSelected(true);
                    updateColors();
                    selectedIcon = index;
                }
            });
        }
    }

    private void setColorClickListeners() {
        for (int i = 0; i < colorCases.length; i++) {
            int index = i;
            colorCases[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedColor = index;
                    updateColors();
                }
            });
        }
    }

    private void updateColors() {
        int color = ContextCompat.getColor(CreateSpaceScreen.this, Utils.listColors[selectedColor]);
        edtSpaceName.setTextColor(color);

        if (selectedIconCase != null) {
            boolean isSelected = selectedIconCase.isSelected();
            selectedIconCase.setColorFilter(color);

            if (isSelected) {
                selectedIconCase.setBackgroundResource(R.drawable.bg_icon_case);
            } else {
                selectedIconCase.setColorFilter(Color.WHITE);
            }
        }
    }
}
