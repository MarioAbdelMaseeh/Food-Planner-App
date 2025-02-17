package com.mario.mychef.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.firebase.database.IgnoreExtraProperties;

@Entity(tableName = "Meals", primaryKeys = {"mealId", "userId", "dateAndFav"})

@IgnoreExtraProperties
public class MealDataBaseModel {
    @NonNull
    private String mealId;
    @NonNull
    private String userId;
    @NonNull
    private String dateAndFav;
    private MealsResponse.MealDTO meal;

    public MealDataBaseModel() {
    }

    public MealDataBaseModel(String mealId, String userId, @NonNull String dateAndFav, MealsResponse.MealDTO meal) {
        this.mealId = mealId;
        this.userId = userId;
        this.dateAndFav = dateAndFav;
        this.meal = meal;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    @NonNull
    public String getDateAndFav() {
        return dateAndFav;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDateAndFav(@NonNull String dateAndFav) {
        this.dateAndFav = dateAndFav;
    }

    public MealsResponse.MealDTO getMeal() {
        return meal;
    }

    public void setMeal(MealsResponse.MealDTO meal) {
        this.meal = meal;
    }
}
