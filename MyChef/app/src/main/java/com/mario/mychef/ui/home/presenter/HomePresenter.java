package com.mario.mychef.ui.home.presenter;

import com.mario.mychef.models.MealsDTO;

public interface HomePresenter {
    public void getMeals();
    public void addMealToPlan(MealsDTO.MealDTO meal);
}
