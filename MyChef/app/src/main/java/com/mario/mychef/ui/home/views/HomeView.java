package com.mario.mychef.ui.home.views;

import com.mario.mychef.models.MealsResponse;

import java.util.List;

public interface HomeView {
    public void showMeals(List<MealsResponse.MealDTO> meals);
    public void showError(String errMsg);
    public void showDetails(MealsResponse.MealDTO meal);
    public void showDailyMeal(MealsResponse.MealDTO meal);
    public void showRandomCategoryMeals(List<MealsResponse.MealDTO> meals);
    public void showRandomCountryMeals(List<MealsResponse.MealDTO> meals);
}
