package com.mario.mychef.db;

import android.content.Context;

import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private final MealsDAO mealsDAO;
    public static MealsLocalDataSourceImpl localDataSource;

    public MealsLocalDataSourceImpl(Context context) {
        AppDataBase appDataBase = AppDataBase.getInstance(context);
        mealsDAO = appDataBase.getMealsDao();
        localDataSource = this;
    }

    public static MealsLocalDataSourceImpl getInstance(Context context) {
        if (localDataSource == null) {
            localDataSource = new MealsLocalDataSourceImpl(context);
        }
        return localDataSource;
    }
    @Override
    public Single<List<MealsResponse.MealDTO>> getStoredFavoritesMeals(String userId) {
        return mealsDAO.getFavoritesMeals(userId);
    }

    @Override
    public Single<List<MealsResponse.MealDTO>> getStoredPlanMeals(String date, String userId) {
        return mealsDAO.getPlanMeals(date,userId);
    }

    @Override
    public Completable insertMeal(MealDataBaseModel meal) {
       return mealsDAO.insertMealInToPlan(meal);

    }
    @Override
    public Completable deleteMeal(MealDataBaseModel meal) {
        return mealsDAO.deleteMeal(meal);
    }
}