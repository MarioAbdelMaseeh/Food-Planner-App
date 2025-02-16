package com.mario.mychef.converters;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.mario.mychef.models.MealsResponse;

public class MealTypeConverter {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromMealDTO(MealsResponse.MealDTO meal) {
        return gson.toJson(meal);
    }

    @TypeConverter
    public static MealsResponse.MealDTO toMealDTO(String mealJson) {
        return gson.fromJson(mealJson, MealsResponse.MealDTO.class);
    }
}
