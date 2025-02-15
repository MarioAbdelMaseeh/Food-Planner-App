package com.mario.mychef.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealsDAO {
    @Query("SELECT Meal FROM Meals ")
    Observable<List<MealsResponse.MealDTO>> getMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMealInToPlan(MealDataBaseModel meal);
    @Delete
    Completable deleteMeal (MealDataBaseModel meal);
}
