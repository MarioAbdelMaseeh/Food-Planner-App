package com.mario.mychef.ui.home.presenter;

import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface HomePresenter {
    public void getMealsByFirstLetter(String firstLetter);
    public void addMealToPlan(MealsResponse.MealDTO meal);
    public void getDetails();
    public void getDailyMeal();
    public void disposeCompositeDisposable();
}
