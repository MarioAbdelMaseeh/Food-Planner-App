package com.mario.mychef.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mario.mychef.models.MealsDTO;

@Database(entities = {MealsDTO.MealDTO.class }, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;
    public abstract MealsDAO getMealsDao();
    public static synchronized AppDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,AppDataBase.class,"mealsDB").build();
            return instance;
        }else {
            return instance;
        }
    }

}

