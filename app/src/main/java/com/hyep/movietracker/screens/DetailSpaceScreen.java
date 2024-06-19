package com.hyep.movietracker.screens;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import com.hyep.movietracker.Listeners.LoadMoviesCallback;
import com.hyep.movietracker.R;
import com.hyep.movietracker.adapter.DetailSpaceAdapter;
import com.hyep.movietracker.helper.FirestoreHelper;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.screens.fragment.BottomSheetSettingSpaceFragment;
import com.hyep.movietracker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DetailSpaceScreen extends AppCompatActivity{

    private TextView tvSpaceName, tvSize;

    private ImageButton imgBtnBack, imgBtnSpaceSetting, imgBtnSearch, imgBtnNote;

    private RecyclerView rvItemSpace;

    private ImageView ivLogo;

    private FirestoreHelper firestoreHelper;

    private List<Movie> movies;

    private DetailSpaceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_detail_space);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
        ivLogo = findViewById(R.id.ivLogo);
        rvItemSpace = findViewById(R.id.rvItemSpace);
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        imgBtnSpaceSetting = (ImageButton) findViewById(R.id.imgBtnSpaceSetting); 
        imgBtnSearch = (ImageButton) findViewById(R.id.imgBtnSearch);
        imgBtnNote = (ImageButton) findViewById(R.id.imgBtnNote);
        tvSpaceName = (TextView) findViewById(R.id.tvSpaceName);
        tvSize = (TextView) findViewById(R.id.tvSize);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int size = intent.getIntExtra("size",0);
        int color = intent.getIntExtra("color",0);
        int icon = intent.getIntExtra("icon",0);

        int trueColor = ContextCompat.getColor(this, Utils.listColors[color]);
        tvSpaceName.setText(name);
        tvSpaceName.setTextColor(trueColor);
        if (size < 2) {
            tvSize.setText(size + " Movie");
        } else {
            tvSize.setText(size + " Movies");
        }
        ivLogo.setImageResource(Utils.listIcons[icon]);
        ivLogo.getDrawable().setColorFilter(getResources().getColor(Utils.listColors[color]), PorterDuff.Mode.SRC_IN);
        imgBtnSearch.getBackground().setColorFilter(new PorterDuffColorFilter(trueColor, PorterDuff.Mode.SRC_IN));
        imgBtnNote.getBackground().setColorFilter(new PorterDuffColorFilter(trueColor, PorterDuff.Mode.SRC_IN));

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                Intent intent = new Intent(DetailSpaceScreen.this, SearchScreen.class);
                startActivity(intent);
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



        movies = new ArrayList<Movie>();

        firestoreHelper = new FirestoreHelper(this);

//        movies.add(new Movie(653346,"/gKkl37BQuKTanygYQG1pyYgLVgf.jpg","Kingdom of the Planet of the Apes"));
//        movies.add(new Movie(1111873,"/gKkl37BQuKTanygYQG1pyYgLVgf.jpg","Abigail"));
//        movies.add(new Movie(748783,"/zK2sFxZcelHJRPVr242rxy5VK4T.jpg","The Garfield Movie"));
//        movies.add(new Movie(872585,"/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg","Oppenheimer"));
//        movies.add(new Movie(1041613,"/fdZpvODTX5wwkD0ikZNaClE4AoW.jpg","Immaculate"));
//        movies.add(new Movie(618588,"/gxVcBc4VM0kAg9wX4HVg6KJHG46.jpg","Arthur the King"));
//        movies.add(new Movie(1063879,"/dY98PkUAbIGUUg0FhXEcOkbzHIZ.jpg","Vermines"));

        adapter = new DetailSpaceAdapter(movies,this, getSupportFragmentManager());
        setUpMoviesList(getIntent().getStringExtra("id"));
        rvItemSpace.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMoviesList(getIntent().getStringExtra("id"));
    }

    private void setUpMoviesList(String spaceId) {
        firestoreHelper.loadMoviesInSpace(spaceId, new LoadMoviesCallback() {
            @Override
            public void onLoaded(List<Movie> movieList) {
                movies.clear();
                movies.addAll(movieList);
                adapter.notifyDataSetChanged();
            }
        });
    }

}