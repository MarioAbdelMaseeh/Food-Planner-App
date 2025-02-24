package com.mario.mychef.db;

import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalDataSource {
    Single<List<MealsResponse.MealDTO>> getStoredFavoritesMeals(String userId);
    Single<List<MealsResponse.MealDTO>> getStoredPlanMeals(String date, String userId);
    Completable insertMeal(MealDataBaseModel meal);
    Completable deleteMeal(MealDataBaseModel meal);
}
