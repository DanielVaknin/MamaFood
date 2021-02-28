package com.daniel.mamafood.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class ModelFirebase {
    public void getAllMeals(Long lastUpdated, final Model.GetAllMealsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(lastUpdated, 0);
        db.collection("meals")
                .whereGreaterThanOrEqualTo("lastUpdated", ts)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Meal> data = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Meal meal = new Meal();
                                meal.fromMap(document.getData());
                                data.add(meal);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        listener.onComplete(data);
                    }
                });
    }

    public void getMeal(String id, Model.GetMealListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("meals").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Meal meal = null;
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        meal = new Meal();
                        meal.fromMap(document.getData());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                }
                listener.onComplete(meal);
            }
        });
    }

    public void addMeal(Meal meal, Model.AddMealListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("meals").document(meal.getId())
                .set(meal.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Meal added successfully!");
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding meal", e);
                        listener.onComplete();
                    }
                });
    }

    public void updateMeal(Meal meal, Model.UpdateMealListener listener) {
        addMeal(meal, listener);
    }

    // TODO: Fix this with a delete marker on a meal to know when a meal is deleted
//    public void deleteMeal(Meal meal, Model.DeleteMealListener listener) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("meals").document(meal.getId())
//                .delete()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        listener.onComplete();
//                    }
//                });
//    }

    public void uploadImage(Bitmap imageBmp, String name, Model.UploadImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }
}
