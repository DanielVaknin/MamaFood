package com.daniel.mamafood.ui.meals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.model.Model;

import java.util.List;

public class MealsViewModel extends ViewModel {

    private LiveData<List<Meal>> mealList;

    public MealsViewModel(){
        mealList = Model.instance.getAllMeals();
    }

    LiveData<List<Meal>> getMealList() {
        return mealList;
    }
}