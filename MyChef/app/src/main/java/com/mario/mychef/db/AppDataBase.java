package com.mario.mychef.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mario.mychef.converters.MealTypeConverter;
import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsResponse;

@Database(entities = {MealDataBaseModel.class }, version = 1)
@TypeConverters({MealTypeConverter.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;
    public abstract MealsDAO getMealsDao();
    public static synchronized AppDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,AppDataBase.class,"mealsDB").build();
        }
        return instance;
    }

}

