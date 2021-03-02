package com.daniel.mamafood.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.daniel.mamafood.MyApplication;

@Database(entities = {Meal.class}, version = 6)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract MealDao mealDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "mamaFood.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
