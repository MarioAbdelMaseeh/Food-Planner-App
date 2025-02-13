package com.mario.mychef.network;


import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.IngredientsResponse;
import com.mario.mychef.models.MealsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface MealsService {
    @GET("search.php")
    Single<MealsResponse> getMealsByFirstLetter(@Query("f") String firstLetter);
    @GET("random.php")
    Single<MealsResponse> getRandomMeal();
    @GET("lookup.php")
    Single<MealsResponse> getMealById(@Query("i") String id);
    @GET("filter.php")
    Single<MealsResponse> getMealsByCategory(@Query("c") String category);
    @GET("filter.php")
    Single<MealsResponse> getMealsByArea(@Query("a") String area);
    @GET("filter.php")
    Single<MealsResponse> getMealsByIngredient(@Query("i") String ingredient);
    @GET("search.php")
    Single<MealsResponse> getMealsByName(@Query("s") String name);
    @GET("latest.php")
    Single<MealsResponse> getLatestMeals();
    @GET("popular.php")
    Single<MealsResponse> getPopularMeals();
    @GET("list.php?i=list")
    Single<IngredientsResponse> getIngredients();
    @GET("list.php?a=list")
    Single<MealsResponse> getAreas();
    @GET("categories.php")
    Single<CategoriesResponse> getCategories();
}
