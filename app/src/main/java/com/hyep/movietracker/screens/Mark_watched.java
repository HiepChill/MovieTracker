package com.hyep.movietracker.screens;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hyep.movietracker.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Mark_watched extends AppCompatActivity {
    private EditText editTextDate;
    private ImageView calendarIcon;
    private int pickedYear, pickedMonth, pickedDay;

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
        int year, month, day;
        if (pickedYear != 0 && pickedMonth != 0 && pickedDay != 0) {
            year = pickedYear;
            month = pickedMonth;
            day = pickedDay;
        }
        else {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Mark_watched.this,
                R.style.my_dialog_theme,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    pickedYear = year1;
                    pickedMonth = monthOfYear;
                    pickedDay = dayOfMonth;

                    // Set the date in the EditText
                    editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                },
                year, month, day);


        datePickerDialog.show();

    }
}