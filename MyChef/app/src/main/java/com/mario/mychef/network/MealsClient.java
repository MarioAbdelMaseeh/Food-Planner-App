package com.mario.mychef.network;

import com.mario.mychef.models.MealsDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsClient {
    public static final String BASE_URL = "https://www.themealdb.com/";
    public MealsService mealsService;
    private static MealsClient mealsClient = null;

    public MealsClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        mealsService = retrofit.create(MealsService.class);
        mealsClient = this;
    }

    public static synchronized MealsClient getInstance(){
        if(mealsClient == null){
            return new MealsClient();
        }else{
            return mealsClient;
        }
    }
    public void makeNetworkCall(NetworkCallback networkCallback){
        Call<MealsDTO> call = mealsService.getMeals();
        call.enqueue(new Callback<MealsDTO>() {
            @Override
            public void onResponse(Call<MealsDTO> call, Response<MealsDTO> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    networkCallback.onSuccessRequest(response.body().getMeals());
                }else{
                    networkCallback.onFailureRequest(response.message());
                }
            }

            @Override
            public void onFailure(Call<MealsDTO> call, Throwable t) {
                networkCallback.onFailureRequest(t.getMessage());
            }
        });
    }
}
