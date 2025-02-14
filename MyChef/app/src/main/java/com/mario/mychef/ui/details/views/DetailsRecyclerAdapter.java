package com.mario.mychef.ui.details.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mario.mychef.R;
import com.mario.mychef.models.IngredientsAndMeasuresDTO;

import java.util.ArrayList;
import java.util.Random;

public class DetailsRecyclerAdapter extends RecyclerView.Adapter<DetailsRecyclerAdapter.ViewHolder> {
    private final ArrayList<IngredientsAndMeasuresDTO> ingredientsAndMeasuresDTOArrayList;
    private int[] colorArray;

    public DetailsRecyclerAdapter(ArrayList<IngredientsAndMeasuresDTO> ingredientsAndMeasuresDTOArrayList) {
        this.ingredientsAndMeasuresDTOArrayList = ingredientsAndMeasuresDTOArrayList;
    }

    @NonNull
    @Override
    public DetailsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.meal_detail_ingredients, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsRecyclerAdapter.ViewHolder holder, int position) {
        holder.detailsMeasure.setText(ingredientsAndMeasuresDTOArrayList.get(position).getMeasure());
        holder.detailsIngredient.setText(ingredientsAndMeasuresDTOArrayList.get(position).getIngredient());
        String url = "https://www.themealdb.com/images/ingredients/"+ingredientsAndMeasuresDTOArrayList.get(position).getIngredient()+"-Small.png";
        Glide.with(holder.itemView.getContext()).load(url).into(holder.ingredientImage);
        holder.detailsImageCard.setCardBackgroundColor(colorArray[new Random().nextInt(colorArray.length)]);
    }

    @Override
    public int getItemCount() {
        return ingredientsAndMeasuresDTOArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ingredientImage;
        TextView detailsMeasure;
        TextView detailsIngredient;
        CardView detailsImageCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientImage = itemView.findViewById(R.id.ingredientImage);
            detailsMeasure = itemView.findViewById(R.id.detailsMeasure);
            detailsIngredient = itemView.findViewById(R.id.detailsIngredient);
            detailsImageCard = itemView.findViewById(R.id.detailsImageCard);
            colorArray = new int[]{
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_blue),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_purple),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_yellow),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_red),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_orange),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_green),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_pink),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_gray)
            };
        }
    }
}
