package com.mario.mychef.ui.favorites;

import android.content.Context;

import com.mario.mychef.models.MealsResponse;

import java.util.List;

public interface FavoritesContract {
    interface View{
        void showMeals(List<MealsResponse.MealDTO> meals);
        void showMessage(String message);
        Context getViewContext();
    }
    interface Presenter{
        void getMeals();
        void deleteMeal(MealsResponse.MealDTO meal);
    }
}
