package com.hyep.movietracker.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.Helper.MyHelper;
import com.hyep.movietracker.R;
import com.hyep.movietracker.Realm.MovieRealm;
import com.hyep.movietracker.adapter.RealmAdapter;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class RealmDB extends AppCompatActivity {

    EditText editText;
    Button button;
    Realm realm;
    RealmChangeListener realmChangeListener;

    RecyclerView recyclerView;
    MyHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_realm_db);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        realm = Realm.getDefaultInstance();
        editText = findViewById(R.id.edtRealm);
        button = findViewById(R.id.btnRealm);
        recyclerView = findViewById(R.id.rvRealm);

        button.setOnClickListener((view -> {
            saveData();
        }));

        helper = new MyHelper(realm);
        helper.selectFromDB();

        RealmAdapter adapter = new RealmAdapter(helper.justRefresh(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        refresh();
    }

    private void saveData() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MovieRealm movie = realm.createObject(MovieRealm.class);
                movie.setTitle(editText.getText().toString());
            }
        });
    }

    public void refresh() {
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                RealmAdapter adapter = new RealmAdapter(helper.justRefresh(), RealmDB.this);
                recyclerView.setAdapter(adapter);
            }
        };
        realm.addChangeListener(realmChangeListener);
    }
}