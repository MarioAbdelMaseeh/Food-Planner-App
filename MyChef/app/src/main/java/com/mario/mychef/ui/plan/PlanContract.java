package com.mario.mychef.ui.plan;

import com.mario.mychef.models.MealsResponse;

import java.util.List;

public interface PlanContract {
    interface PlanView {
        void showMeals(List<MealsResponse.MealDTO> meals);
        void showError(String message);
    }
    interface PlanPresenter {
        void getPlanMeals(String date);
        void deleteMeal(MealsResponse.MealDTO meal,String date);
    }
}
