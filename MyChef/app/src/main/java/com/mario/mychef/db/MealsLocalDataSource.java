package com.mario.mychef.db;

import com.mario.mychef.models.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MealsLocalDataSource {
    Observable<List<MealsResponse.MealDTO>> getStoredMeals();
    void insertMeal(MealsResponse.MealDTO meal);
    void deleteMeal(MealsResponse.MealDTO meal);
}
