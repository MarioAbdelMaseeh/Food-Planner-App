package com.mario.mychef.models;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Optional;

import io.reactivex.rxjava3.core.Single;

public interface MealsRepo {
    LiveData<List<MealsResponse.MealDTO>> getStoredMeals();
    void insertMeal(MealsResponse.MealDTO meal);
    void deleteMeal(MealsResponse.MealDTO meal);
    Single<MealsResponse> getMealsByFirstLetter(String firstLetter);
    Single<CategoriesResponse> getCategories();
    Single<IngredientsResponse> getIngredients();
    Single<CountryResponse> getCountries();
}
