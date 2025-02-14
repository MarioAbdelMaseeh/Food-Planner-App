package com.mario.mychef.ui.meals;

import com.mario.mychef.models.MealsResponse;

import java.util.List;

public interface MealsContract {
    interface MealsView {
        void showMeals(List<MealsResponse.MealDTO> meals);
        void showError(String message);
    }
    interface MealsPresenter {
        void getMeals(String type, String name);
    }
}
