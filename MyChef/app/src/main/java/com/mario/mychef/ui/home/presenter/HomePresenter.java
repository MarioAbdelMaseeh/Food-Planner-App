package com.mario.mychef.ui.home.presenter;

import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface HomePresenter {
    public void getMealsByFirstLetter(String firstLetter);
    public void getDetails();
    public void getDailyMeal();
    void getMealsByCategory(String category);
    void getMealsByArea(String area);
    public void disposeCompositeDisposable();
}
