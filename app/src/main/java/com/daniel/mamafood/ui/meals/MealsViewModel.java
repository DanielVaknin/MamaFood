package com.daniel.mamafood.ui.meals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.daniel.mamafood.model.Meal;

import java.util.LinkedList;
import java.util.List;

public class MealsViewModel extends ViewModel {

//    private MutableLiveData<String> mText;
//
//    public MealsViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }

    private List<Meal> mealList = new LinkedList<>();

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }
}