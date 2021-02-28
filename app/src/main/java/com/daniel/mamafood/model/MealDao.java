package com.daniel.mamafood.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDao {

    @Query("select * from Meal")
    LiveData<List<Meal>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Meal... meals);

    @Delete
    void delete(Meal meal);
}
