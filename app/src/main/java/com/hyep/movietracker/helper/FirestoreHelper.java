
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.models.TagModel;
import com.hyep.movietracker.screens.CreateSpaceScreen;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FirestoreHelper {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private Context context;

    public FirestoreHelper(Context con) {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        context = con;
    }

    public void createSpace(PersonalSpaceModel space) {
        Map<String, Object> spaceData = new HashMap<>();
        spaceData.put("id", space.getId());
        spaceData.put("name", space.getName());
        spaceData.put("number", space.getSize());
        spaceData.put("color", space.getColor());
        spaceData.put("icon", space.getIcon());

        db.collection("users")
                .document(user.getUid())
                .collection("spaces")
                .document(space.getId())
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
    }

    public void createTag(TagModel tag) {
        Map<String, Object> tagData = new HashMap<>();
        tagData.put("id", tag.getId());
        tagData.put("name", tag.getName());
        tagData.put("color", tag.getColor());

        db.collection("users")
                .document(user.getUid())
                .collection("tags")
                .document(tag.getId())
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

}