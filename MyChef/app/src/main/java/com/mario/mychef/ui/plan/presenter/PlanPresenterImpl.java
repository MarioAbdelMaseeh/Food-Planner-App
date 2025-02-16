package com.mario.mychef.ui.plan.presenter;

import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.ui.plan.PlanContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImpl implements PlanContract.PlanPresenter{
    private PlanContract.PlanView view;
    private MealsRepo repo;
    public PlanPresenterImpl(PlanContract.PlanView view, MealsRepo repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getPlanMeals(String date) {
        repo.getStoredPlanMeals(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> view.showMeals(meals), throwable -> view.showError(throwable.getMessage()));
    }

    @Override
    public void deleteMeal(MealsResponse.MealDTO meal,String date) {
        repo.deleteMeal(new MealDataBaseModel(meal.getIdMeal(),1,date,meal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getPlanMeals(date), throwable -> view.showError(throwable.getMessage()));
    }
}
