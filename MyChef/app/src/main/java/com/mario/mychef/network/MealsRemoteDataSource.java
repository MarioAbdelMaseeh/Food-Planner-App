package com.mario.mychef.network;

import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.CountryResponse;
import com.mario.mychef.models.IngredientsResponse;
import com.mario.mychef.models.MealsResponse;

import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource {
    public Single<MealsResponse> getMealByFirstLetter(String firstLetter);
    public Single<CategoriesResponse> getCategories();

    Single<IngredientsResponse> getIngredients();
    Single<CountryResponse> getCountries();
}

