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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hyep.movietracker.models.PersonalSpaceModel;
import com.hyep.movietracker.models.TagModel;

import java.util.ArrayList;
import java.util.HashMap;
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

    public void loadSpaces() {
        ArrayList<PersonalSpaceModel> spacesList = new ArrayList<>();
        spaces.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document != null) {
                                    Log.d("Firestore", document.getId() + " => " + document.getData());
                                    String id = document.getId();
                                    String name = document.getString("name");
                                    int size = Objects.requireNonNull(document.getLong("size")).intValue();
                                    int color = Objects.requireNonNull(document.getLong("color")).intValue();
                                    int icon = Objects.requireNonNull(document.getLong("icon")).intValue();
                                    PersonalSpaceModel space = new PersonalSpaceModel(id, name, size, color, icon);
                                    spacesList.add(space);
                                }
                            }
                        }
                        else {
                            Log.e("FirestoreError", "Error getting documents", task.getException());
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
}
