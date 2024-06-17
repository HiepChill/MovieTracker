package com.hyep.movietracker.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.DetailSpaceAdapter;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DetailTagScreen extends AppCompatActivity {

    private ImageButton imgBtnBack;

    private TextView tvNameTag;

    private RecyclerView rcvMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_detail_tag);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgBtnBack = findViewById(R.id.imgBtnBack);
        tvNameTag = findViewById(R.id.tvNameTag);
        rcvMovie = findViewById(R.id.rcvMovie);

        Intent intent = getIntent();
        String nameTag = intent.getStringExtra("name");
        int colorId = intent.getIntExtra("color", 0);
        int color = ContextCompat.getColor(this, Utils.listColors[colorId]);

        imgBtnBack.setOnClickListener(view -> {
            finish();
        });

        tvNameTag.setText("#" + nameTag);
        tvNameTag.setTextColor(color);

        List<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie(653346,"/gKkl37BQuKTanygYQG1pyYgLVgf.jpg","Kingdom of the Planet of the Apes"));
        movies.add(new Movie(1111873,"/gKkl37BQuKTanygYQG1pyYgLVgf.jpg","Abigail"));
        movies.add(new Movie(748783,"/zK2sFxZcelHJRPVr242rxy5VK4T.jpg","The Garfield Movie"));
        movies.add(new Movie(872585,"/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg","Oppenheimer"));
        movies.add(new Movie(1041613,"/fdZpvODTX5wwkD0ikZNaClE4AoW.jpg","Immaculate"));
        movies.add(new Movie(618588,"/gxVcBc4VM0kAg9wX4HVg6KJHG46.jpg","Arthur the King"));
        movies.add(new Movie(1063879,"/dY98PkUAbIGUUg0FhXEcOkbzHIZ.jpg","Vermines"));

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return 1;
            }
        });
        rcvMovie.setLayoutManager(layoutManager);

        DetailSpaceAdapter adapter = new DetailSpaceAdapter(movies,this, getSupportFragmentManager());

        rcvMovie.setAdapter(adapter);

    }
}