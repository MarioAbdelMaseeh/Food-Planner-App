package com.mario.mychef.models;

import androidx.lifecycle.LiveData;

import com.mario.mychef.network.NetworkCallback;

import java.util.List;

public interface MealsRepo {
    LiveData<List<MealsDTO.MealDTO>> getStoredMeals();
    void insertMeal(MealsDTO.MealDTO meal);
    void deleteMeal(MealsDTO.MealDTO meal);
    void getMeals(NetworkCallback networkCallback);
}
