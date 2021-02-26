package com.daniel.mamafood.model;

import android.graphics.Bitmap;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();

    ModelFirebase modelFirebase = new ModelFirebase();

    private Model(){}

    public interface Listener<T> {
        void onComplete(T result);
    }

    public interface GetAllMealsListener extends Listener<List<Meal>> {}
    public void getAllMeals(GetAllMealsListener listener){
        modelFirebase.getAllMeals(listener);
    }

    public interface GetMealListener extends Listener<Meal>{}
    public void getMeal(String id, GetMealListener listener) {
        modelFirebase.getMeal(id, listener);
    }

    public interface AddMealListener {
        void onComplete();
    }
    public void addMeal(Meal meal, AddMealListener listener){
        modelFirebase.addMeal(meal, listener);
    }

    public interface UpdateMealListener extends AddMealListener {}
    public void updateMeal(Meal meal, UpdateMealListener listener){
        modelFirebase.updateMeal(meal, listener);
    }

    public interface DeleteMealListener extends AddMealListener {}
    public void deleteMeal(Meal meal, DeleteMealListener listener) {
        modelFirebase.deleteMeal(meal, listener);
    }

    public interface UploadImageListener extends Listener<String> {}
    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener){
        modelFirebase.uploadImage(imageBmp, name, listener);
    }
}
