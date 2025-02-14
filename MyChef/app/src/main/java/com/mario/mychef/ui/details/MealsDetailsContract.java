package com.mario.mychef.ui.details;

import com.mario.mychef.models.MealsResponse;

public interface MealsDetailsContract {
    interface MealsDetailsView {
        void showMealDetails(MealsResponse.MealDTO meal);
        void showError(String message);
    }
    interface MealsDetailsPresenter {
        void getMealDetails(String id);
    }
}
