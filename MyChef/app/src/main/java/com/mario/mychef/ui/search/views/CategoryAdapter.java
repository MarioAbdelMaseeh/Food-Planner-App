package com.mario.mychef.ui.search.views;

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
import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.ui.RandomColorGenerator;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<CategoriesResponse.CategoriesDTO> categoriesDTOList;

    public CategoryAdapter(List<CategoriesResponse.CategoriesDTO> categoriesDTOList) {
        this.categoriesDTOList = categoriesDTOList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.category_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.categoryText.setText(categoriesDTOList.get(position).getStrCategory());
        Glide.with(holder.itemView.getContext()).load(categoriesDTOList.get(position)
                .getStrCategoryThumb()).into(holder.categoryImage);
        holder.categoryCard.setCardBackgroundColor(RandomColorGenerator.getRandomColor(holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return categoriesDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage;
        TextView categoryText;
        CardView categoryCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryText = itemView.findViewById(R.id.categoryText);
            categoryCard = itemView.findViewById(R.id.categoryCard);
        }
    }

    public void setCategoriesDTOList(List<CategoriesResponse.CategoriesDTO> categoriesDTOList) {
        this.categoriesDTOList = categoriesDTOList;
    }

}
