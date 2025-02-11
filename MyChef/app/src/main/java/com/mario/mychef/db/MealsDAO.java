package com.mario.mychef.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mario.mychef.models.MealsDTO;

import java.util.List;
@Dao
public interface MealsDAO {
    @Query("SELECT * FROM Meals ")
    LiveData<List<MealsDTO.MealDTO>> getAllMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealsDTO.MealDTO meal);
    @Delete
    void deleteMeal (MealsDTO.MealDTO meal);
}
