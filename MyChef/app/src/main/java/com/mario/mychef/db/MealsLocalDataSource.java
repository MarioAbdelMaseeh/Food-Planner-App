package com.mario.mychef.db;

import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface MealsLocalDataSource {
    Observable<List<MealsResponse.MealDTO>> getStoredFavoritesMeals();
    Completable insertMeal(MealDataBaseModel meal);
    Completable deleteMeal(MealDataBaseModel meal);
}
