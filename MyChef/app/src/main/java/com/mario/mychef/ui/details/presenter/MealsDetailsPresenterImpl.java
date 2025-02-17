package com.mario.mychef.ui.details.presenter;

import com.mario.mychef.firedb.FireDataBase;
import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.sharedpreference.SharedPreferenceManager;
import com.mario.mychef.ui.details.MealsDetailsContract;

import java.util.Objects;

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
        MealDataBaseModel mealDataBaseModel = new MealDataBaseModel(meal.getIdMeal()
                , SharedPreferenceManager.getInstance(view.getViewContext()).getUserId()
                ,"Fav",meal);

        FireDataBase.getInstance().saveMeal(mealDataBaseModel.getUserId()
                        ,mealDataBaseModel.getDateAndFav(),mealDataBaseModel.getMealId(),mealDataBaseModel)
                .addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                repo.insertMeal(mealDataBaseModel)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> view.showMessage("Meal added to favorites")
                                , throwable -> view.showMessage(throwable.getMessage()));
            }else{
                view.showMessage(Objects.requireNonNull(task.getException()).getMessage());
            }
        });

    }

    @Override
    public void addMealToPlan(MealsResponse.MealDTO meal, String date) {
        MealDataBaseModel mealDataBaseModel = new MealDataBaseModel(meal.getIdMeal()
            , SharedPreferenceManager.getInstance(view.getViewContext()).getUserId()
            ,date,meal);

        FireDataBase.getInstance().saveMeal(mealDataBaseModel.getUserId(),mealDataBaseModel.getDateAndFav(),mealDataBaseModel.getMeal().getIdMeal(),mealDataBaseModel)
                .addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                repo.insertMeal(mealDataBaseModel).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> view.showMessage("Meal added to plan")
                                , throwable -> view.showMessage(throwable.getMessage()));
            }else{
                view.showMessage(Objects.requireNonNull(task.getException()).getMessage());
            }
        });

    }


}
