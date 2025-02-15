package com.mario.mychef.models;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface MealsRepo {
    Observable<List<MealsResponse.MealDTO>> getStoredMeals();
    void insertMeal(MealsResponse.MealDTO meal);
    void deleteMeal(MealsResponse.MealDTO meal);
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
