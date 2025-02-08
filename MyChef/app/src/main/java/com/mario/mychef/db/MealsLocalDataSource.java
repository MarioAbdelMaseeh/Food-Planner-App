package com.mario.mychef.db;

import androidx.lifecycle.LiveData;

import com.mario.mychef.models.MealsDTO;

import java.util.List;

public interface MealsLocalDataSource {
    LiveData<List<MealsDTO>> getStoredMeals();
    void insertMeal(MealsDTO meal);
    void deleteMeal(MealsDTO meal);
}
