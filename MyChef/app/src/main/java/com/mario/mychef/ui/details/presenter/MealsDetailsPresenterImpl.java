package com.mario.mychef.ui.details.presenter;

import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.ui.details.MealsDetailsContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsDetailsPresenterImpl implements MealsDetailsContract.MealsDetailsPresenter {

    private MealsDetailsContract.MealsDetailsView view;
    private MealsRepo repo;

    public MealsDetailsPresenterImpl(MealsDetailsContract.MealsDetailsView view, MealsRepo repo) {
        this.view = view;
        this.repo = repo;
    }
    @Override
    public void getMealDetails(String id) {
        repo.getMealById(id)
                .map(obj -> obj.getMeals().get(0))
                .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    view.showMealDetails(meal);
                    view.setMeal(meal);}, throwable -> view.showMessage(throwable.getMessage()));

    }

    @Override
    public void addMealToFav(MealsResponse.MealDTO meal) {
        repo.insertMeal(new MealDataBaseModel(meal.getIdMeal(),1,"Fav",meal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.showMessage("Meal added to favorites")
                        , throwable -> view.showMessage(throwable.getMessage()));
    }

}
