package com.mario.mychef.ui.details;

import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsResponse;

public interface MealsDetailsContract {
    interface MealsDetailsView {
        void showMealDetails(MealsResponse.MealDTO meal);
        void showMessage(String message);
        void setMeal(MealsResponse.MealDTO meal);
    }
    interface MealsDetailsPresenter {
        void getMealDetails(String id);
        void addMealToFav(MealsResponse.MealDTO meal);
        void addMealToPlan(MealDataBaseModel meal);
    }
}
