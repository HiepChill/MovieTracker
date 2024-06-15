package com.hyep.movietracker.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hyep.movietracker.models.PersonalSpaceModel;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FirestoreHelper {
    FirebaseFirestore db;
    FirebaseUser user;
    String userId;

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
    }

    public void createSpace() {
        PersonalSpaceModel space = new PersonalSpaceModel(
                "Movies of duong",
                10,
                1,
                1
        );
        db.collection("users")
                .document(userId);
        Map<String, Object> spaceDetail = new HashMap<>();
        spaceDetail.put("name", space.getName());
        spaceDetail.put("color", space.getColor());
        spaceDetail.put("icon", space.getIcon());
//        db.collection("users")
//                .document(userId)
//                .collection("spaces")
//                .document(generateId(space.getName()))
//                .set(spaceDetail);
    }

    public String generateId(String string) {
        String normalized = Normalizer.normalize(string, Normalizer.Form.NFD);

        // Loại bỏ các dấu bằng cách loại bỏ các ký tự kết hợp
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noDiacritics = pattern.matcher(normalized).replaceAll("");

        // Chuyển tất cả các ký tự thành chữ thường
        noDiacritics = noDiacritics.toLowerCase();

        // Loại bỏ khoảng trắng và ký tự đặc biệt
        noDiacritics = noDiacritics.replaceAll("\\s+", "");
        noDiacritics = noDiacritics.replaceAll("[^a-z0-9]", "");

        return noDiacritics;
    }
}
