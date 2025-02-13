package com.mario.mychef.ui.search.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mario.mychef.R;
import com.mario.mychef.models.IngredientsResponse;
import com.mario.mychef.ui.RandomColorGenerator;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    List<IngredientsResponse.IngredientDTO> ingredientDTOList;
    public IngredientsAdapter(List<IngredientsResponse.IngredientDTO> ingredientDTOList) {
        this.ingredientDTOList = ingredientDTOList;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.ingrenient_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        holder.ingredientText.setText(ingredientDTOList.get(position).getStrIngredient());
        String url = "https://www.themealdb.com/images/ingredients/"+ingredientDTOList.get(position).getStrIngredient()+"-Small.png";
        Glide.with(holder.itemView.getContext()).load(url).into(holder.ingredientItemImage);
        holder.ingredientCard.setCardBackgroundColor(RandomColorGenerator.getRandomColor(holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return ingredientDTOList.size();
    }

    public void setIngredientDTOList(List<IngredientsResponse.IngredientDTO> ingredients) {
        this.ingredientDTOList = ingredients;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ingredientItemImage;
        TextView ingredientText;
        CardView ingredientCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientItemImage = itemView.findViewById(R.id.ingredientItemImage);
            ingredientText = itemView.findViewById(R.id.ingredientText);
            ingredientCard = itemView.findViewById(R.id.ingredientCard);
        }
    }
}
