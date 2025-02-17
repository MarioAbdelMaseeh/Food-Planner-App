package com.mario.mychef.models;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface MealsRepo {
    Single<List<MealsResponse.MealDTO>> getStoredFavoritesMeals(String userId);
    Single<List<MealsResponse.MealDTO>> getStoredPlanMeals(String date, String userId);
    Completable insertMeal(MealDataBaseModel meal);
    Completable deleteMeal(MealDataBaseModel meal);
    Single<MealsResponse> getMealsByFirstLetter(String firstLetter);
    Single<CategoriesResponse> getCategories();
    Single<IngredientsResponse> getIngredients();
    Single<CountryResponse> getCountries();
    Single<MealsResponse> getRandomMeal();
    Single<MealsResponse> getMealById(String id);
    Single<MealsResponse> getMealsByCategory(String category);
    Single<MealsResponse> getMealsByArea(String area);
    Single<MealsResponse> getMealsByIngredient(String ingredient);
    Single<MealsResponse> getMealsByName(String name);
}
