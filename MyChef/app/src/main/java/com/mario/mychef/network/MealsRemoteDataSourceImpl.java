package com.mario.mychef.network;

import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.IngredientsResponse;
import com.mario.mychef.models.MealsResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource{
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public MealsService mealsService;
    private static MealsRemoteDataSourceImpl mealsRemoteDataSourceImpl = null;

    public MealsRemoteDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealsService = retrofit.create(MealsService.class);
        mealsRemoteDataSourceImpl = this;
    }

    public static synchronized MealsRemoteDataSourceImpl getInstance(){
        if(mealsRemoteDataSourceImpl == null){
            return new MealsRemoteDataSourceImpl();
        }else{
            return mealsRemoteDataSourceImpl;
        }
    }

    @Override
    public Single<MealsResponse> getMealByFirstLetter(String firstLetter) {
        return mealsService.getMealsByFirstLetter(firstLetter);
    }

    @Override
    public Single<CategoriesResponse> getCategories() {
        return mealsService.getCategories();
    }

    @Override
    public Single<IngredientsResponse> getIngredients() {
        return mealsService.getIngredients();
    }
}
