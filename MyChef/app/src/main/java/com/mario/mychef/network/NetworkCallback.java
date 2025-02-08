package com.mario.mychef.network;

import com.mario.mychef.models.MealsDTO;

import java.util.List;

public interface NetworkCallback {
    public void onSuccessRequest(List<MealsDTO.MealDTO> meals);
    public void onFailureRequest(String errMsg);
}
