package com.mario.mychef.network;

import com.mario.mychef.models.MealsDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealsService {
    @GET("api/json/v1/1/search.php?f=b")
    Call<MealsDTO> getMeals();
}
