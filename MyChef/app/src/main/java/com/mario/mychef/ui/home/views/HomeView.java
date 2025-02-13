package com.mario.mychef.ui.home.views;

import com.mario.mychef.models.MealsResponse;

import java.util.List;

public interface HomeView {
    public void showMeals(List<MealsResponse.MealDTO> meals);
    public void showError(String errMsg);
}
