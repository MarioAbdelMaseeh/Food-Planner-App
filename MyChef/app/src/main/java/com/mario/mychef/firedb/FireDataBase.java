package com.mario.mychef.firedb;

import static kotlinx.coroutines.flow.FlowKt.observeOn;

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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
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
                long count = snapshot.getChildrenCount();
                Log.i("TAG", "onDataChange: "+ count);
                for (DataSnapshot dateSnapshot : snapshot.getChildren()) { // Iterate over dates
                    String dateAndFav = dateSnapshot.getKey(); // "17-02-2025" or "Fav"
                    Log.i("TAG", "Date key: " + dateSnapshot.getKey());
                    for (DataSnapshot mealSnapshot : dateSnapshot.getChildren()) { // Iterate over meals
                        String mealId = mealSnapshot.getKey(); // "52777" or "52865"
                        Log.i("TAG", "Meal key: " + mealSnapshot.getKey()); // Should print meal IDs like "52777"
                        Log.i("TAG", "Meal data: " + mealSnapshot.getValue().toString()); // Full meal object
                        // Extract nested meal data
                        MealsResponse.MealDTO mealDTO = mealSnapshot.child("meal").getValue(MealsResponse.MealDTO.class);
                        String userId = mealSnapshot.child("userId").getValue(String.class);
                        Log.i("TAG", "Extracted mealDTO: " + mealDTO.getIdMeal());
                        Log.i("TAG", "Extracted userId: " + userId);

                        if (mealDTO != null && userId != null) {
                            MealDataBaseModel mealModel = new MealDataBaseModel(mealId, userId, dateAndFav, mealDTO);
                            meals.add(mealModel);
                        }
                    }
                }

                if (!meals.isEmpty()) {
                    Observable.fromIterable(meals)
                            .subscribeOn(Schedulers.io())
                            .flatMap(meal -> repo.insertMeal(meal)
                                    .subscribeOn(Schedulers.io())
                                    .toObservable()
                            )
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(x-> Log.i("TAG", "All meals inserted successfully"),
                                    throwable -> Log.e("TAG", "Error inserting meals: " + throwable.getMessage())
                            );
                } else {
                    Log.i("TAG", "No meals found in Firebase.");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: "+error.getMessage());
            }
        });

    }
}

