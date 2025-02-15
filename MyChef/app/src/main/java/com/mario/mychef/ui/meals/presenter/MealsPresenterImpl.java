package com.mario.mychef.ui.meals.presenter;

import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.ui.meals.MealsContract;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsPresenterImpl implements MealsContract.MealsPresenter {
    MealsRepo mealsRepo;
    MealsContract.MealsView mealsView;
    List<MealsResponse.MealDTO> meals;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MealsPresenterImpl(MealsRepo mealsRepo, MealsContract.MealsView mealsView) {
        this.mealsRepo = mealsRepo;
        this.mealsView = mealsView;
    }
    @Override
    public void getMeals(String type, String name) {
        Disposable disposable;
        switch (type){
            case "firstLetter":

                break;
            case "category":
                disposable = mealsRepo.getMealsByCategory(name).map(MealsResponse::getMeals).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            mealsView.showMeals(list);
                            meals = list;
                                }
                                , throwable -> mealsView.showError(throwable.getMessage()));
               compositeDisposable.add(disposable);
                break;
            case "area":
                disposable = mealsRepo.getMealsByArea(name).map(MealsResponse::getMeals).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            mealsView.showMeals(list);
                            meals = list;
                                }
                                , throwable -> mealsView.showError(throwable.getMessage()));
                compositeDisposable.add(disposable);
                break;
            case "ingredient":
                disposable = mealsRepo.getMealsByIngredient(name).map(MealsResponse::getMeals).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            mealsView.showMeals(list);
                            meals = list;
                        }, throwable -> mealsView.showError(throwable.getMessage()));
                compositeDisposable.add(disposable);
                break;
            case "name":
                disposable = Observable.fromIterable(meals)
                        .filter(meal -> meal.getStrMeal().toLowerCase().contains(name.toLowerCase()))
                        .toList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> mealsView.showMeals(list), throwable -> mealsView.showError(throwable.getMessage()));
                compositeDisposable.add(disposable);
                break;
        }
    }
}
