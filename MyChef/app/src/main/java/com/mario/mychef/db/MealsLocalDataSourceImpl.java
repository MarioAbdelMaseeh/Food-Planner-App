package com.mario.mychef.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mario.mychef.models.MealsDTO;

import java.util.List;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private MealsDAO mealsDAO;
    private LiveData<List<MealsDTO.MealDTO>> meals;
    public static MealsLocalDataSourceImpl localDataSource;

    public MealsLocalDataSourceImpl(Context context) {
        AppDataBase appDataBase = AppDataBase.getInstance(context);
        mealsDAO = appDataBase.getMealsDao();
        meals = mealsDAO.getAllMeals();
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
    public LiveData<List<MealsDTO.MealDTO>> getStoredMeals() {
        return meals;
    }

    @Override
    public void insertMeal(MealsDTO.MealDTO meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.insertMeal(meal);
            }
        }).start();

    }
    @Override
    public void deleteMeal(MealsDTO.MealDTO meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.deleteMeal(meal);
            }
        }).start();
    }
}