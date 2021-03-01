package com.daniel.mamafood.ui.meals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.model.Model;

import java.util.ArrayList;
import java.util.List;

public class MealsViewModel extends ViewModel {

    LiveData<List<Meal>> mealLiveData;

    public MealsViewModel() {
        mealLiveData = Model.instance.getAllMeals();
    }

    public LiveData<List<Meal>> getMealLiveData() {
        return mealLiveData;
    }
}