
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
import com.hyep.movietracker.Listeners.DeleteSpaceCallback;
import com.hyep.movietracker.Listeners.DeleteTagCallback;
import com.hyep.movietracker.Listeners.LoadMovieByIdCallback;
import com.hyep.movietracker.Listeners.LoadMoviesCallback;
import com.hyep.movietracker.Listeners.LoadSpacesCallback;
import com.hyep.movietracker.Listeners.LoadTagsCallback;
import com.hyep.movietracker.Listeners.LoadTagsInMovieCallback;
import com.hyep.movietracker.api.APIClient;
import com.hyep.movietracker.models.Movie;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.models.TagModel;
import com.hyep.movietracker.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirestoreHelper {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private Context context;
    private CollectionReference spaces, tags, movies;

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
        movies = db.collection("users")
                .document(user.getUid())
                .collection("movies");
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

    public void updateSpace(String id, String name, int color, int icon) {
        Map<String, Object> spaceData = new HashMap<>();
        spaceData.put("name", name);
        spaceData.put("color", color);
        spaceData.put("icon", icon);
        spaces.document(id)
                .update(spaceData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Space updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to update space name: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("FirestoreError", "Error updating document", e);
                    }
                });
    }

    public void deleteSpace(String id, final DeleteSpaceCallback deleteSpaceCallback) {
        spaces.document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Space deleted successfully", Toast.LENGTH_SHORT).show();
                        deleteSpaceCallback.onDeleted();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to delete space: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteTag(String id, final DeleteTagCallback deleteTagCallback) {
        tags.document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Tag deleted successfully", Toast.LENGTH_SHORT).show();
                        deleteTagCallback.onDeleted();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to delete tag: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void addMovieToSpace(String spaceId, String movieId) {
        CollectionReference moviesRef = spaces.document(spaceId).collection("movies");

        moviesRef.document(movieId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Toast.makeText(context, "Movie already exists in this space", Toast.LENGTH_SHORT).show();
                    } else {
                        moviesRef.document(movieId)
                                .set(new HashMap<>())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Movie added successfully", Toast.LENGTH_SHORT).show();
                                        spaces.document(spaceId).update("size", FieldValue.increment(1));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed to add movie: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("FirestoreError", "Error adding document", e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to check movie: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreError", "Error checking document", e);
                });
    }


    public void addMovieToTag(String tagId, String movieId) {
        CollectionReference moviesRef = tags.document(tagId).collection("movies");

        moviesRef.document(movieId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Toast.makeText(context, "Movie already exists in this tag", Toast.LENGTH_SHORT).show();
                    } else {
                        moviesRef.document(movieId)
                                .set(new HashMap<>())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Movie added successfully", Toast.LENGTH_SHORT).show();
                                        tags.document(tagId).update("size", FieldValue.increment(1));
                                        addTagToMovie(movieId, tagId);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed to add movie: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("FirestoreError", "Error adding document", e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to check movie: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("FirestoreError", "Error checking document", e);
                });
    }

    public void addTagToMovie(String movieId, String tagId) {
        tags.document(tagId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String tagName = documentSnapshot.getString("name");

                        Map<String, Object> tagData = new HashMap<>();
                        tagData.put("id", tagId);
                        tagData.put("name", tagName);

                        movies.document(movieId)
                                .collection("tags")
                                .document(tagId)
                                .set(tagData)
                                .addOnSuccessListener(unused -> Log.d("FirestoreSuccess", "Tag added to movie successfully"))
                                .addOnFailureListener(e -> Log.e("FirestoreError", "Error adding document", e));
                    } else {
                        Log.e("FirestoreError", "Tag document does not exist");
                    }
                })
                .addOnFailureListener(e -> Log.e("FirestoreError", "Error getting tag document", e));
    }

    public void loadTagsInMovie(String movieId, final LoadTagsInMovieCallback loadTagsInMovieCallback) {
        movies.document(movieId)
                .collection("tags")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> tagsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document != null) {
                                    String tagName = document.getString("name");
                                    if (tagName != null) {
                                        tagsList.add(tagName);
                                    }
                                }
                            }
                            loadTagsInMovieCallback.onLoaded(tagsList);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document != null) {
                                    int movieId = Integer.parseInt(document.getId());
                                    loadMovieById(movieId, new LoadMovieByIdCallback() {
                                        @Override
                                        public void onLoaded(Movie movie) {
                                            movies.add(movie);
                                            if (movies.size() == task.getResult().size()) {
                                                loadMoviesCallback.onLoaded(movies);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loadMoviesInTag(String tagId, final LoadMoviesCallback loadMoviesCallback) {
        tags.document(tagId)
                .collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Movie> movies = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document != null) {
                                    int movieId = Integer.parseInt(document.getId());
                                    loadMovieById(movieId, new LoadMovieByIdCallback() {
                                        @Override
                                        public void onLoaded(Movie movie) {
                                            movies.add(movie);
                                            if (movies.size() == task.getResult().size()) {
                                                loadMoviesCallback.onLoaded(movies);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loadMovieById(int movieId, final LoadMovieByIdCallback loadMovieByIdCallback) {
        APIClient.getApiInterface().getDetailMovieById(movieId, Utils.API_KEY, Utils.LANGUAGE_ENGLISH).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();

                String title = movie.getTitle();
                String posterPath = Utils.BASE_IMG_URL + movie.getPosterPath();
                Movie returnedMovie = new Movie(movieId, posterPath, title);

                loadMovieByIdCallback.onLoaded(returnedMovie);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable throwable) {
                Toast.makeText(context, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
