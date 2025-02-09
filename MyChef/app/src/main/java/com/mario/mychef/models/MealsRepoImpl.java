package com.mario.mychef.models;

import androidx.lifecycle.LiveData;

import com.mario.mychef.db.MealsLocalDataSource;
import com.mario.mychef.network.MealsRemoteDataSource;
import com.mario.mychef.network.NetworkCallback;

import java.util.List;

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
    public LiveData<List<MealsDTO.MealDTO>> getStoredMeals() {
        return mealsLocalDataSource.getStoredMeals();
    }

    @Override
    public void insertMeal(MealsDTO.MealDTO meal) {
        mealsLocalDataSource.insertMeal(meal);
    }

    @Override
    public void deleteMeal(MealsDTO.MealDTO meal) {
        mealsLocalDataSource.deleteMeal(meal);
    }

    @Override
    public void getMeals(NetworkCallback networkCallback) {
        mealsRemoteDataSource.makeNetworkCall(networkCallback);
    }
}
