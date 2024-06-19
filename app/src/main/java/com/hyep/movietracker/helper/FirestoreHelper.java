
package com.hyep.movietracker.helper;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hyep.movietracker.Listeners.LoadMoviesCallback;
import com.hyep.movietracker.Listeners.LoadSpacesCallback;
import com.hyep.movietracker.Listeners.LoadTagsCallback;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.models.TagModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirestoreHelper {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private Context context;
    private CollectionReference spaces, tags;

    public FirestoreHelper(Context con) {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        context = con;
        spaces = db.collection("users")
                .document(user.getUid())
                .collection("spaces");
        tags = db.collection("users")
                .document(user.getUid())
                .collection("tags");
    }

    public void loadSpaces(final LoadSpacesCallback loadSpacesCallback) {
        spaces.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<PersonalSpaceModel> spacesList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document != null) {
                                    PersonalSpaceModel space = document.toObject(PersonalSpaceModel.class);
                                    spacesList.add(space);
                                }
                            }
                            Toast.makeText(context, "Loaded " + spacesList.size() + " spaces", Toast.LENGTH_SHORT).show();
                            loadSpacesCallback.onLoaded(spacesList);
                        }
                        else {
                            Toast.makeText(context, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                            Log.e("FirestoreError", "Error getting documents", task.getException());
                            loadSpacesCallback.onLoaded(new ArrayList<>());
                        }
                    }
                });
    }

    public void loadTags(final LoadTagsCallback loadTagsCallback) {
        tags.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<TagModel> tagsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document != null) {
                                    TagModel tag = document.toObject(TagModel.class);
                                    tagsList.add(tag);
                                }
                            }
                            Toast.makeText(context, "Loaded " + tagsList.size() + " tags", Toast.LENGTH_SHORT).show();
                            loadTagsCallback.onLoaded(tagsList);
                        }
                        else {
                            Toast.makeText(context, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                            Log.e("FirestoreError", "Error getting documents", task.getException());
                            loadTagsCallback.onLoaded(new ArrayList<>());
                        }
                    }
                });
    }

    public void createSpace(PersonalSpaceModel space) {
        Map<String, Object> spaceData = new HashMap<>();
        spaceData.put("id", space.getId());
        spaceData.put("name", space.getName());
        spaceData.put("size", space.getSize());
        spaceData.put("color", space.getColor());
        spaceData.put("icon", space.getIcon());

        spaces.document(space.getId())
                .set(spaceData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Fail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("FirestoreError", "Error adding document", e);
                    }
                });
    }

    public void createTag(TagModel tag) {
        Map<String, Object> tagData = new HashMap<>();
        tagData.put("id", tag.getId());
        tagData.put("name", tag.getName());
        tagData.put("size", tag.getSize());
        tagData.put("color", tag.getColor());

        tags.document(tag.getId())
                .set(tagData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Fail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("FirestoreError", "Error adding document", e);
                    }
                });
    }

    public void addMovieToSpace(String spaceId, String movieId) {
        db.collection("users")
                .document(user.getUid())
                .collection("spaces")
                .document(spaceId)
                .collection("movies")
                .document(movieId)
                .set(new HashMap<>()) // Lưu trữ document rỗng với movieId làm ID
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Movie added successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, "Failed to add movie: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.e("FirestoreError", "Error adding document", e);
                    }
                });
        spaces.document(spaceId).update("size", FieldValue.increment(1));
    }

    public void addMovieToTag(String tagId, String movieId) {
        db.collection("users")
                .document(user.getUid())
                .collection("tags")
                .document(tagId)
                .collection("movies")
                .document(movieId)
                .set(new HashMap<>()) // Lưu trữ document rỗng với movieId làm ID
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Movie added successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, "Failed to add movie: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.e("FirestoreError", "Error adding document", e);
                    }
                });
        spaces.document(tagId).update("size", FieldValue.increment(1));
    }

    public void loadMoviesInSpace(String spaceId, final LoadMoviesCallback loadMoviesCallback) {
        spaces.document(spaceId)
                .collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Movie> movies = new ArrayList<>();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
