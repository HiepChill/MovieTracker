package com.hyep.movietracker.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hyep.movietracker.R;

public class Utils {
    public static String API_KEY = "0b9dffdf2cc5ef177909e12da7782207";
    public static String BASE_URL = "https://api.themoviedb.org/3/";
    public static String BASE_IMG_URL = "https://image.tmdb.org/t/p/w500";

    public static String LANGUAGE_ENGLISH = "en-US";
    public static String LANGUAGE_VIETNAMESE = "vi-VN";

    public static int[] listColors = {
            R.color.royalBlue,
            R.color.purple,
            R.color.magenta,
            R.color.aquaGreen,
            R.color.chromeYellow,
            R.color.bluePurple,
            R.color.blazeOrange,
            R.color.red,
            R.color.claret,
            R.color.smokeyGrey,
            R.color.purpleJam,
            R.color.brown,
            R.color.green,
            R.color.cobaltBlue,
            R.color.skyBlue,
    };

    public static int[] listIcons = {
            R.drawable.ic_space_logo_1,
            R.drawable.ic_space_logo_2,
            R.drawable.ic_space_logo_3,
            R.drawable.ic_space_logo_4,
            R.drawable.ic_space_logo_5,
            R.drawable.ic_space_logo_6,
    };

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void setHideKeyboardOnTouch(Activity activity, View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(activity);
                return false;
            }
        });
    }
}
