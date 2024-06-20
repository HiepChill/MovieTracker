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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

    private String id, name;

    private int size, color, icon, trueColor;

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
        id = intent.getStringExtra("id");
        size = intent.getIntExtra("size",0);
        name = intent.getStringExtra("name");
        color = intent.getIntExtra("color",0);
        icon = intent.getIntExtra("icon",0);

        if (size < 2) {
            tvSize.setText(size + " Movie");
        } else {
            tvSize.setText(size + " Movies");
        }

        setUpDetailSpace(name, color, icon);

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgBtnSpaceSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetSettingSpaceFragment bottomSheetSettingSpaceFragment = new BottomSheetSettingSpaceFragment(id);
                bottomSheetSettingSpaceFragment.show(getSupportFragmentManager(), bottomSheetSettingSpaceFragment.getTag());
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

        adapter = new DetailSpaceAdapter(movies,this, getSupportFragmentManager());
        setUpMoviesList(id);
        rvItemSpace.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMoviesList(getIntent().getStringExtra("id"));
        updateDetailSpace();
    }

    private void setUpDetailSpace(String name, int color, int icon) {
        trueColor = ContextCompat.getColor(this, Utils.listColors[color]);
        tvSpaceName.setText(name);
        tvSpaceName.setTextColor(trueColor);

        ivLogo.setImageResource(Utils.listIcons[icon]);
        ivLogo.getDrawable().setColorFilter(getResources().getColor(Utils.listColors[color]), PorterDuff.Mode.SRC_IN);
        imgBtnSearch.getBackground().setColorFilter(new PorterDuffColorFilter(trueColor, PorterDuff.Mode.SRC_IN));
        imgBtnNote.getBackground().setColorFilter(new PorterDuffColorFilter(trueColor, PorterDuff.Mode.SRC_IN));
    }

    private void updateDetailSpace() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(user.getUid())
                .collection("spaces")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                name = document.getString("name");
                                color = document.getLong("color").intValue();
                                icon = document.getLong("icon").intValue();
                                setUpDetailSpace(name, color, icon);
                            }
                            else {
                                Toast.makeText(DetailSpaceScreen.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(DetailSpaceScreen.this, "Error getting document", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetailSpaceScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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