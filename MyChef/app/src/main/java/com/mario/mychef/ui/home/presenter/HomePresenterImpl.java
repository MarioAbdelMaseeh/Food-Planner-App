package com.mario.mychef.ui.home.presenter;

import com.mario.mychef.models.MealsDTO;
import com.mario.mychef.network.NetworkCallback;
import com.mario.mychef.ui.home.views.HomeView;

import java.util.List;

public class HomePresenterImpl implements HomePresenter , NetworkCallback {
    private HomeView homeView;

    @Override
    public void onSuccessRequest(List<MealsDTO.MealDTO> meals) {

    }

    @Override
    public void onFailureRequest(String errMsg) {

    }

    @Override
    public void getMeals() {

    }

    @Override
    public void addMealToPlan(MealsDTO.MealDTO meal) {

    }
}
