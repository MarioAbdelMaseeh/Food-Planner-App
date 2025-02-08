package com.mario.mychef.ui.home.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mario.mychef.R;
import com.mario.mychef.models.MealsDTO;

import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {
    List<MealsDTO.MealDTO> meals;

    public HomeRecyclerAdapter(List<MealsDTO.MealDTO> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public HomeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.meal_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.ViewHolder holder, int position) {
        holder.mealName.setText(meals.get(position).getStrMeal());
        Glide.with(holder.itemView.getContext()).load(meals.get(position).getStrMealThumb()).into(holder.mealImg);
        holder.addToFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add to fav
            }
        });
    }
    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<MealsDTO.MealDTO> meals) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImg;
        Button addToFavBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealCardName);
            mealImg = itemView.findViewById(R.id.mealCardImage);
            addToFavBtn = itemView.findViewById(R.id.addToFavBtn);
        }
    }
}
