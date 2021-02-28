package com.daniel.mamafood.model;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelSql {

    public LiveData<List<Meal>> getAllMeals(){
        return AppLocalDb.db.mealDao().getAllMeals();
    }

    public interface AddMealListener{
        void onComplete();
    }
    public void addMeal(Meal meal, Model.AddMealListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.mealDao().insertAll(meal);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener  != null)
                    listener.onComplete();
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }
}
