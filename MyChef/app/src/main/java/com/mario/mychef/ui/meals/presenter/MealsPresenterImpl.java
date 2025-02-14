package com.mario.mychef.ui.meals.presenter;

import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.ui.meals.MealsContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsPresenterImpl implements MealsContract.MealsPresenter {
    MealsRepo mealsRepo;
    MealsContract.MealsView mealsView;
    public MealsPresenterImpl(MealsRepo mealsRepo, MealsContract.MealsView mealsView) {
        this.mealsRepo = mealsRepo;
        this.mealsView = mealsView;
    }
    @Override
    public void getMeals(String type, String name) {
        switch (type){
            case "firstLetter":

                break;
            case "category":
                mealsRepo.getMealsByCategory(name).map(MealsResponse::getMeals).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mealsView::showMeals, throwable -> mealsView.showError(throwable.getMessage()));
                break;
            case "area":
                mealsRepo.getMealsByArea(name).map(MealsResponse::getMeals).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mealsView::showMeals, throwable -> mealsView.showError(throwable.getMessage()));
                break;
            case "ingredient":
                mealsRepo.getMealsByIngredient(name).map(MealsResponse::getMeals).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mealsView::showMeals, throwable -> mealsView.showError(throwable.getMessage()));
                break;
            case "name":

                break;
        }
    }
}
