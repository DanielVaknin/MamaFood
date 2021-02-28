package com.daniel.mamafood.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.daniel.mamafood.MyApplication;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    ModelFirebase modelFirebase = new ModelFirebase();
    ModelSql modelSql = new ModelSql();

    private Model(){

    }

    public interface Listener<T> {
        void onComplete(T result);
    }

    public interface GetAllMealsListener extends Listener<List<Meal>> {}

    LiveData<List<Meal>> mealList;
    public LiveData<List<Meal>> getAllMeals(){
        if (mealList == null){
            mealList = modelSql.getAllMeals();
            refreshAllMeals(null);
        }
        return mealList;
    }

    public void refreshAllMeals(final Listener listener){
        // Get local last update date
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdated", 0);

        // Get all updated records from Firebase from last update date
        modelFirebase.getAllMeals(lastUpdated, new GetAllMealsListener() {
            @Override
            public void onComplete(List<Meal> result) {
                // Insert the new updated to the local DB
                long lastU = 0;
                for (Meal s: result) {
                    modelSql.addMeal(s, null);
                    if (s.getLastUpdated() > lastU)
                        lastU = s.getLastUpdated();
                }
                // Update the local last update date
                sp.edit().putLong("lastUpdated", lastU).commit();

                // Return the updated data to the listeners
                if (listener != null) {
                    listener.onComplete(null);
                }
            }
        });
    }

    public interface GetMealListener {
        void onComplete(Meal meal);
    }
    public void getMeal(String id, GetMealListener listener) {
        modelFirebase.getMeal(id, listener);
    }

    public interface AddMealListener{
        void onComplete();
    }
    public void addMeal(Meal meal, final AddMealListener listener){
        modelFirebase.addMeal(meal, new AddMealListener() {
            @Override
            public void onComplete() {
                refreshAllMeals(new GetAllMealsListener() {
                    @Override
                    public void onComplete(List<Meal> result) {
                        listener.onComplete();
                    }
                });
            }
        });
    }

    public interface UpdateMealListener extends AddMealListener {}
    public void updateMeal(Meal meal, UpdateMealListener listener){
        modelFirebase.updateMeal(meal, listener);
    }

//    public interface DeleteMealListener extends AddMealListener {}
//    public void deleteMeal(Meal meal, DeleteMealListener listener) {
//        modelFirebase.deleteMeal(meal, listener);
//    }

    public interface UploadImageListener extends Listener<String> {}
    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener){
        modelFirebase.uploadImage(imageBmp, name, listener);
    }
}
