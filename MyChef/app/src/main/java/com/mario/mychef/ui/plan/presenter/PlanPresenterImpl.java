package com.mario.mychef.ui.plan.presenter;

import android.util.Log;

import com.mario.mychef.firedb.FireDataBase;
import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.sharedpreference.SharedPreferenceManager;
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
        repo.getStoredPlanMeals(date,SharedPreferenceManager.getInstance(view.getViewContext()).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals ->{ view.showMeals(meals);
                    Log.i("TAG", "getPlanMeals: "+meals.size() );}, throwable -> {
                    view.showError(throwable.getMessage());
                    Log.e("TAG", "getPlanMeals: "+throwable.getMessage() );});
    }

    @Override
    public void deleteMeal(MealsResponse.MealDTO meal,String date) {
        MealDataBaseModel mealDataBaseModel = new MealDataBaseModel(meal.getIdMeal(), SharedPreferenceManager.getInstance(view.getViewContext()).getUserId(),date,meal);
        Log.i("TAG", "deleteMeal: "+mealDataBaseModel.getMealId()+" "+mealDataBaseModel.getDateAndFav()+" "+mealDataBaseModel.getUserId() );
        FireDataBase.getInstance().deleteMeal(SharedPreferenceManager.getInstance(view.getViewContext()).getUserId(),mealDataBaseModel.getDateAndFav(),mealDataBaseModel.getMealId(),mealDataBaseModel)
                .addOnFailureListener(e->{
            Log.e("TAG", "deleteMeal: "+e.getMessage() );
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Log.i("TAG", "deleteMeal: "+task.getResult() );
                repo.deleteMeal(new MealDataBaseModel(meal.getIdMeal(), SharedPreferenceManager.getInstance(view.getViewContext()).getUserId(),date,meal))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> getPlanMeals(date), throwable -> view.showError(throwable.getMessage()));
            }else {
                Log.e("TAG", "deleteMeal: "+task.getException().getMessage() );
            }
                });

    }
}
