package com.hyep.movietracker.screens;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.CardMovieAdapter;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.screens.fragment.BottomSheetSettingSpaceFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailSpaceScreen extends AppCompatActivity{

    
    private ImageButton imgBtnBack, imgBtnSpaceSetting, imgBtnSearch, imgBtnNote;

    private RecyclerView rvItemSpace;

    private ImageView ivLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_detail_space);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ivLogo = findViewById(R.id.ivLogo);
        rvItemSpace = findViewById(R.id.rvItemSpace);
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        imgBtnSpaceSetting = (ImageButton) findViewById(R.id.imgBtnSpaceSetting); 
        imgBtnSearch = (ImageButton) findViewById(R.id.imgBtnSearch);
        imgBtnNote = (ImageButton) findViewById(R.id.imgBtnNote);

        ivLogo.getDrawable().setColorFilter(getResources().getColor(R.color.magenta), PorterDuff.Mode.SRC_IN);

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailSpaceScreen.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });
        
        
        imgBtnSpaceSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetSettingSpaceFragment bottomSheetSettingSpaceFragment = new BottomSheetSettingSpaceFragment();
                bottomSheetSettingSpaceFragment.show(getSupportFragmentManager(),bottomSheetSettingSpaceFragment.getTag());
            }
        });

        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailSpaceScreen.this, "Search", Toast.LENGTH_SHORT).show();
            }
        });
        
        imgBtnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailSpaceScreen.this, "Note", Toast.LENGTH_SHORT).show();
            }
        });



        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return 1;
            }
        });
        rvItemSpace.setLayoutManager(layoutManager);



        List<Movie> movies = new ArrayList<Movie>();
        movies.add(new Movie(653346,"/gKkl37BQuKTanygYQG1pyYgLVgf.jpg","Kingdom of the Planet of the Apes"));
        movies.add(new Movie(1111873,"/gKkl37BQuKTanygYQG1pyYgLVgf.jpg","Abigail"));
        movies.add(new Movie(748783,"/zK2sFxZcelHJRPVr242rxy5VK4T.jpg","The Garfield Movie"));
        movies.add(new Movie(872585,"/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg","Oppenheimer"));
        movies.add(new Movie(1041613,"/fdZpvODTX5wwkD0ikZNaClE4AoW.jpg","Immaculate"));
        movies.add(new Movie(618588,"/gxVcBc4VM0kAg9wX4HVg6KJHG46.jpg","Arthur the King"));
        movies.add(new Movie(1063879,"/dY98PkUAbIGUUg0FhXEcOkbzHIZ.jpg","Vermines"));

        CardMovieAdapter adapter = new CardMovieAdapter(movies,this, getSupportFragmentManager());

        rvItemSpace.setAdapter(adapter);
    }

}