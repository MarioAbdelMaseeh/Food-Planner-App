package com.mario.mychef.db;

import androidx.lifecycle.LiveData;

import com.mario.mychef.models.MealsResponse;

import java.util.List;

public interface MealsLocalDataSource {
    LiveData<List<MealsResponse.MealDTO>> getStoredMeals();
    void insertMeal(MealsResponse.MealDTO meal);
    void deleteMeal(MealsResponse.MealDTO meal);
}
