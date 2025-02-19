package com.mario.mychef.firedb;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mario.mychef.db.MealsLocalDataSourceImpl;
import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsRepoImpl;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.network.MealsRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FireDataBase {
    private final DatabaseReference myDatabase;
    private static FireDataBase instance = null;
    private MealsRepo repo;

    public FireDataBase() {
        myDatabase = FirebaseDatabase.getInstance().getReference("users");
        instance = this;
    }

    public static synchronized FireDataBase getInstance() {
        return Objects.requireNonNullElseGet(instance, FireDataBase::new);
    }
    public Task<Void> saveMeal(String userId, String date, String mealId, MealDataBaseModel meal){
       return myDatabase.child(userId).child(date).child(mealId).setValue(meal);
    }
    public Task<Void> deleteMeal(String userId, String date, String mealId, MealDataBaseModel meal){
        return myDatabase.child(userId).child(date).child(mealId).removeValue();
    }
    public void updateMeals(String userId, Context context){
        repo = MealsRepoImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(context));
        List<MealDataBaseModel> meals = new ArrayList<>();
        myDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                    String dateAndFav = dateSnapshot.getKey();
                    for (DataSnapshot mealSnapshot : dateSnapshot.getChildren()) {
                        String mealId = mealSnapshot.getKey();
                        MealsResponse.MealDTO mealDTO = mealSnapshot.child("meal").getValue(MealsResponse.MealDTO.class);
                        String userId = mealSnapshot.child("userId").getValue(String.class);
                        if (mealDTO != null && userId != null) {
                            MealDataBaseModel mealModel = new MealDataBaseModel(mealId, userId, dateAndFav, mealDTO);
                            meals.add(mealModel);
                        }
                    }
                }
                insertMealsInToRoom(meals);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: "+error.getMessage());
            }
        });

    }

    private void insertMealsInToRoom(List<MealDataBaseModel> meals) {
        if (!meals.isEmpty()) {
            Observable.fromIterable(meals)
                    .subscribeOn(Schedulers.io())
                    .flatMap(meal -> repo.insertMeal(meal)
                            .subscribeOn(Schedulers.io())
                            .toObservable()
                    )
                    .subscribe(x-> Log.i("TAG", "All meals inserted successfully"),
                            throwable -> Log.e("TAG", "Error inserting meals: " + throwable.getMessage())
                    );
        } else {
            Log.i("TAG", "No meals found in Firebase.");
        }
    }
}

