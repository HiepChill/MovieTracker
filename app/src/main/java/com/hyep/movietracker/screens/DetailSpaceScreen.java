package com.hyep.movietracker.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.CardMovieAdapter;
import com.hyep.movietracker.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class DetailSpaceScreen extends AppCompatActivity {

    
    private ImageButton imgBtnBack, imgBtnSpaceSetting;

    private RecyclerView rvItemSpace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_detail_space);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        rvItemSpace = findViewById(R.id.rvItemSpace);
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        imgBtnSpaceSetting = (ImageButton) findViewById(R.id.imgBtnSpaceSetting); 
        
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailSpaceScreen.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });
        
        
        imgBtnSpaceSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailSpaceScreen.this, "Setting", Toast.LENGTH_SHORT).show();
            }
        });

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);


        List<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie(653346,"/gKkl37BQuKTanygYQG1pyYgLVgf.jpg","Kingdom of the Planet of the Apes"));
        movies.add(new Movie(1111873,"/gKkl37BQuKTanygYQG1pyYgLVgf.jpg","Abigail"));
        movies.add(new Movie(748783,"/zK2sFxZcelHJRPVr242rxy5VK4T.jpg","The Garfield Movie"));
        movies.add(new Movie(872585,"/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg","Oppenheimer"));
        movies.add(new Movie(1041613,"/fdZpvODTX5wwkD0ikZNaClE4AoW.jpg","Immaculate"));
        movies.add(new Movie(618588,"/gxVcBc4VM0kAg9wX4HVg6KJHG46.jpg","Arthur the King"));
        movies.add(new Movie(1063879,"/dY98PkUAbIGUUg0FhXEcOkbzHIZ.jpg","Vermines"));

        CardMovieAdapter adapter = new CardMovieAdapter(movies,this);
        rvItemSpace.setAdapter(adapter);
    }
}