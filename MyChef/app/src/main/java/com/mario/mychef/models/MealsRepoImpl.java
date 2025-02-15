package com.mario.mychef.models;

import com.mario.mychef.db.MealsLocalDataSource;
import com.mario.mychef.network.MealsRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepoImpl implements MealsRepo{
    private final MealsRemoteDataSource mealsRemoteDataSource;
    private final MealsLocalDataSource mealsLocalDataSource;
    private static MealsRepoImpl instance = null;
    public MealsRepoImpl(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealsLocalDataSource = mealsLocalDataSource;
        instance = this;
    }
    public static synchronized MealsRepoImpl getInstance(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource){
        if(instance == null){
            return new MealsRepoImpl(mealsRemoteDataSource, mealsLocalDataSource);
        }else{
            return instance;
        }
    }
    @Override
    public Observable<List<MealsResponse.MealDTO>> getStoredMeals() {
        return mealsLocalDataSource.getStoredMeals();
    }

    @Override
    public void insertMeal(MealsResponse.MealDTO meal) {
        mealsLocalDataSource.insertMeal(meal);
    }

    @Override
    public void deleteMeal(MealsResponse.MealDTO meal) {
        mealsLocalDataSource.deleteMeal(meal);
    }

    @Override
    public Single<MealsResponse> getMealsByFirstLetter(String firstLetter) {
        return mealsRemoteDataSource.getMealByFirstLetter(firstLetter);
    }

    @Override
    public Single<CategoriesResponse> getCategories() {
        return mealsRemoteDataSource.getCategories();
    }

    @Override
    public Single<IngredientsResponse> getIngredients() {
        return mealsRemoteDataSource.getIngredients();
    }

    @Override
    public Single<CountryResponse> getCountries() {
        return mealsRemoteDataSource.getCountries();
    }

    @Override
    public Single<MealsResponse> getRandomMeal() {
        return mealsRemoteDataSource.getRandomMeal();
    }

    @Override
    public Single<MealsResponse> getMealById(String id) {
        return mealsRemoteDataSource.getMealById(id);
    }

    @Override
    public Single<MealsResponse> getMealsByCategory(String category) {
        return mealsRemoteDataSource.getMealsByCategory(category);
    }

    @Override
    public Single<MealsResponse> getMealsByArea(String area) {
        return mealsRemoteDataSource.getMealsByArea(area);
    }

    @Override
    public Single<MealsResponse> getMealsByIngredient(String ingredient) {
        return mealsRemoteDataSource.getMealsByIngredient(ingredient);
    }

    @Override
    public Single<MealsResponse> getMealsByName(String name) {
        return mealsRemoteDataSource.getMealsByName(name);
    }


}
