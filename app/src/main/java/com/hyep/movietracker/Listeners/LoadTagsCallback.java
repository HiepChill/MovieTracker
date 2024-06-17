package com.hyep.movietracker.Listeners;

import com.hyep.movietracker.models.TagModel;

import java.util.ArrayList;

public interface LoadTagsCallback {
    void onLoaded(ArrayList<TagModel> tags);
}
