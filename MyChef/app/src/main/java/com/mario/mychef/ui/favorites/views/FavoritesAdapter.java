package com.mario.mychef.ui.favorites.views;

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

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>{
    private List<MealsResponse.MealDTO> meals;
    private final FavoritesAdapterListener listener;


    public FavoritesAdapter(List<MealsResponse.MealDTO> meals, FavoritesAdapterListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plan_and_fav_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(meals.get(position).getStrMealThumb()).into(holder.mealImage);
        holder.mealName.setText(meals.get(position).getStrMeal());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteClick(meals.get(holder.getAdapterPosition()));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMealClick(meals.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mealImage;
        TextView mealName;
        FloatingActionButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.planMealCardImage);
            mealName = itemView.findViewById(R.id.planMealCardName);
            deleteBtn = itemView.findViewById(R.id.removeBtn);
        }
    }

    public void setMeals(List<MealsResponse.MealDTO> meals) {
        this.meals = meals;
    }
}
interface FavoritesAdapterListener{
    void onDeleteClick(MealsResponse.MealDTO meal);

    void onMealClick(MealsResponse.MealDTO mealDTO);
}
