package com.mario.mychef.ui.meals.views;

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

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder>{
    private List<MealsResponse.MealDTO> meals;

    public MealsAdapter(List<MealsResponse.MealDTO> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meal_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.ViewHolder holder, int position) {
        holder.mealName.setText(meals.get(position).getStrMeal());
       Glide.with(holder.itemView.getContext()).load(meals.get(position).getStrMealThumb()).into(holder.mealImage);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<MealsResponse.MealDTO> meals) {
        this.meals = meals;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mealImage;
        TextView mealName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealCardImage);
            mealName = itemView.findViewById(R.id.mealCardName);
        }
    }
}
