package com.hyep.movietracker.Listeners;

import com.hyep.movietracker.models.PersonalSpaceModel;

import java.util.ArrayList;

public interface LoadSpacesCallback {
    void onLoaded(ArrayList<PersonalSpaceModel> spaces);
}
