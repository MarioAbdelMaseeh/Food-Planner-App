package com.mario.mychef.ui.favorites.presenter;

import com.mario.mychef.firedb.FireDataBase;
import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.sharedpreference.SharedPreferenceManager;
import com.mario.mychef.ui.favorites.FavoritesContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesPresenter implements FavoritesContract.Presenter {
    private FavoritesContract.View view;
    private MealsRepo mealsRepo;
    public FavoritesPresenter(FavoritesContract.View view, MealsRepo mealsRepo) {
        this.view = view;
        this.mealsRepo = mealsRepo;
    }

    @Override
    public void getMeals() {
        mealsRepo.getStoredFavoritesMeals(SharedPreferenceManager.getInstance(view.getViewContext()).getUserId())
                .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> view.showMeals(meals),throwable -> view.showMessage(throwable.getMessage()));
    }

    @Override
    public void deleteMeal(MealsResponse.MealDTO meal) {
        MealDataBaseModel mealDataBaseModel = new MealDataBaseModel(meal.getIdMeal(), SharedPreferenceManager.getInstance(view.getViewContext()).getUserId(),"Fav",meal);
        FireDataBase.getInstance().deleteMeal(SharedPreferenceManager.getInstance(view.getViewContext()).getUserId(),mealDataBaseModel.getDateAndFav(),mealDataBaseModel.getMealId(),mealDataBaseModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                mealsRepo.deleteMeal(new MealDataBaseModel(meal.getIdMeal(), SharedPreferenceManager.getInstance(view.getViewContext()).getUserId(),"Fav",meal)).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> getMeals(),throwable -> view.showMessage(throwable.getMessage()));
            }
        });

    }
}
