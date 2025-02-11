package com.mario.mychef.db;

import androidx.lifecycle.LiveData;

import com.mario.mychef.models.MealsDTO;

import java.util.List;

public interface MealsLocalDataSource {
    LiveData<List<MealsDTO.MealDTO>> getStoredMeals();
    void insertMeal(MealsDTO.MealDTO meal);
    void deleteMeal(MealsDTO.MealDTO meal);
}
