package com.hyep.movietracker.screens;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
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
import com.hyep.movietracker.models.PersonalSpaceModel;

import com.hyep.movietracker.helper.FirestoreHelper;

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
    private ImageView previousIconCase = null;
    private FirestoreHelper firestoreHelper;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_create_space);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

        Utils.setHideKeyboardOnTouch(this, findViewById(R.id.main));
        setIconClickListeners();
        setColorClickListeners();

        imvArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firestoreHelper = new FirestoreHelper(this);

        mode = getIntent().getStringExtra("mode");
        if (mode.equals("update")) {
            String id = getIntent().getStringExtra("id");
            updateSpace(id);
        }
        if (mode.equals("create")) {
            createNewSpace();
        }
    }

    private void updateSpace(String id) {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestoreHelper.updateSpace(
                        id,
                        edtSpaceName.getText().toString(),
                        selectedColor,
                        selectedIcon);
                finish();
            }
        });
    }

    private void createNewSpace() {
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
                finish();
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

        // Reset the color of the previously selected icon to white, if it exists
        if (previousIconCase != null) {
            previousIconCase.setColorFilter(Color.WHITE);
            previousIconCase.getBackground().setColorFilter(null);  // Reset any background color filter
        }

        // Apply the selected color to the current icon
        if (selectedIconCase != null) {
            boolean isSelected = selectedIconCase.isSelected();
            selectedIconCase.setColorFilter(color);

            if (isSelected) {
                selectedIconCase.getBackground().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            } else {
                selectedIconCase.setColorFilter(Color.WHITE);
            }

            // Update the previousIconCase to the currently selected icon
            previousIconCase = selectedIconCase;
        }
    }
}
