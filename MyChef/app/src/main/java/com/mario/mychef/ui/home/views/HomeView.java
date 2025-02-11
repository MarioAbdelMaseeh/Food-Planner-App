package com.mario.mychef.ui.home.views;

import com.mario.mychef.models.MealsDTO;

import java.util.List;

public interface HomeView {
    public void showMeals(List<MealsDTO.MealDTO> meals);
    public void showError(String errMsg);
}
