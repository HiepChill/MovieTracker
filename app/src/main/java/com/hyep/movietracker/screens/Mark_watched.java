package com.hyep.movietracker.screens;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hyep.movietracker.R;

import java.util.Calendar;

public class Mark_watched extends AppCompatActivity {
    private EditText editTextDate;
    private ImageView calendarIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_show);

        editTextDate = findViewById(R.id.editTextDate);
        calendarIcon = findViewById(R.id.calendarIcon);

        calendarIcon.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Mark_watched.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    // Set the date in the EditText
                    editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                },
                year, month, day);

        datePickerDialog.show();
    }
}
