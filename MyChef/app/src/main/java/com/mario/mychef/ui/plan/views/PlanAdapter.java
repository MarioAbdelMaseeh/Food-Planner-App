package com.mario.mychef.ui.plan.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mario.mychef.R;
import com.mario.mychef.models.MealsResponse;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder>{
    private List<MealsResponse.MealDTO> meals;
    private final PlanAdapterHelper helper;

    public PlanAdapter(List<MealsResponse.MealDTO> meals,PlanAdapterHelper helper) {
        this.helper = helper;
        this.meals = meals;
    }

    @NonNull
    @Override
    public PlanAdapter.PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plan_and_fav_card, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.PlanViewHolder holder, int position) {
        holder.mealName.setText(meals.get(position).getStrMeal());
        Glide.with(holder.itemView.getContext()).load(meals.get(position).getStrMealThumb()).into(holder.mealImage);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.onDeleteClick(meals.get(holder.getAdapterPosition()));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.showDetails(meals.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class PlanViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;
        FloatingActionButton deleteBtn;
        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.planMealCardImage);
            mealName = itemView.findViewById(R.id.planMealCardName);
            deleteBtn = itemView.findViewById(R.id.removeBtn);
        }
    }
    public void setMeals(List<MealsResponse.MealDTO> meals) {
        this.meals = meals;
    }
    interface PlanAdapterHelper{
        void onDeleteClick(MealsResponse.MealDTO meal);
         void showDetails(MealsResponse.MealDTO meal);
    }
}
