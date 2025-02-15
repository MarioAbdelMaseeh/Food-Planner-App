package com.mario.mychef.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mario.mychef.models.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealsDAO {
    @Query("SELECT * FROM Meals ")
    Observable<List<MealsResponse.MealDTO>> getMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealInToPlan(MealsResponse.MealDTO meal);
    @Delete
    void deleteMeal (MealsResponse.MealDTO meal);
}
