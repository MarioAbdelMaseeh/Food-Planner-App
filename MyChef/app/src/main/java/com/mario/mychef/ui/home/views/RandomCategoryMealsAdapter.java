package com.mario.mychef.ui.home.views;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mario.mychef.R;
import com.mario.mychef.models.MealsResponse;

import java.util.List;

public class RandomCategoryMealsAdapter extends RecyclerView.Adapter<RandomCategoryMealsAdapter.ViewHolder> {
    List<MealsResponse.MealDTO> meals;
    private HomeRecyclerAdapterHelper homeRecyclerAdapterHelper;

    public RandomCategoryMealsAdapter(List<MealsResponse.MealDTO> meals, HomeRecyclerAdapterHelper homeRecyclerAdapterHelper) {
        this.meals = meals;
        this.homeRecyclerAdapterHelper =  homeRecyclerAdapterHelper;
    }

    @NonNull
    @Override
    public RandomCategoryMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.meal_card, parent, false);
        return new RandomCategoryMealsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RandomCategoryMealsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.mealName.setText(meals.get(position).getStrMeal());
        Glide.with(holder.itemView.getContext()).load(meals.get(position).getStrMealThumb()).into(holder.mealImg);
        holder.mealImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeRecyclerAdapterHelper.showDetails(meals.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<MealsResponse.MealDTO> meals) {
        this.meals = meals;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealCardName);
            mealImg = itemView.findViewById(R.id.mealCardImage);
        }
    }
}
