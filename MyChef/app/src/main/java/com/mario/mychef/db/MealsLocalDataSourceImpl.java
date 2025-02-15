package com.mario.mychef.db;

import android.content.Context;

import com.mario.mychef.models.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private MealsDAO mealsDAO;
    private Observable<List<MealsResponse.MealDTO>> meals;
    public static MealsLocalDataSourceImpl localDataSource;

    public MealsLocalDataSourceImpl(Context context) {
        AppDataBase appDataBase = AppDataBase.getInstance(context);
        mealsDAO = appDataBase.getMealsDao();
        meals = mealsDAO.getMeals();
        localDataSource = this;
    }

    public static MealsLocalDataSourceImpl getInstance(Context context) {
        if (localDataSource == null) {
            localDataSource = new MealsLocalDataSourceImpl(context);
            return localDataSource;
        } else {
            return localDataSource;
        }
    }
    @Override
    public Observable<List<MealsResponse.MealDTO>> getStoredMeals() {
        return meals;
    }

    @Override
    public void insertMeal(MealsResponse.MealDTO meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.insertMealInToPlan(meal);
            }
        }).start();

    }
    @Override
    public void deleteMeal(MealsResponse.MealDTO meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.deleteMeal(meal);
            }
        }).start();
    }
}