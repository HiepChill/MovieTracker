package com.hyep.movietracker.Helper;

import com.hyep.movietracker.Realm.MovieRealm;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyHelper {
    Realm realm;
    RealmResults<MovieRealm> movie;

    public MyHelper(Realm realm) {
        this.realm = realm;
    }

    public void selectFromDB() {
        movie = realm.where(MovieRealm.class).findAll();
    }

    public ArrayList<MovieRealm> justRefresh() {
        ArrayList<MovieRealm> movieRealms = new ArrayList<>();
        for (MovieRealm m : movie) {
            movieRealms.add(m);
        }

        return movieRealms;
    }
}
