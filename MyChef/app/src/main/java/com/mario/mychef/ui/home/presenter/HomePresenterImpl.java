package com.mario.mychef.ui.home.presenter;

import android.util.Log;

import com.mario.mychef.models.MealsDTO;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.network.NetworkCallback;
import com.mario.mychef.ui.home.views.HomeView;

import java.util.List;

public class HomePresenterImpl implements HomePresenter , NetworkCallback {
    private HomeView homeView;
    private MealsRepo mealsRepo;

    public HomePresenterImpl(HomeView homeView, MealsRepo mealsRepo) {
        this.homeView = homeView;
        this.mealsRepo = mealsRepo;
    }

    @Override
    public void onSuccessRequest(List<MealsDTO.MealDTO> meals) {
        Log.i("TAG", "onSuccessRequest: ");
        homeView.showMeals(meals);
    }

    @Override
    public void onFailureRequest(String errMsg) {
        homeView.showError(errMsg);
    }

    @Override
    public void getMeals() {
        mealsRepo.getMeals(this);
    }

    @Override
    public void addMealToPlan(MealsDTO.MealDTO meal) {

    }
}
