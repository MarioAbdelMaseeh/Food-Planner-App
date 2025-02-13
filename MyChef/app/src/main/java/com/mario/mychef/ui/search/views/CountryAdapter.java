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
import com.mario.mychef.models.CountryResponse;
import com.mario.mychef.ui.RandomColorGenerator;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    List<CountryResponse.CountryDTO> countryDTOS;
    public CountryAdapter(List<CountryResponse.CountryDTO> countryDTOS) {
        this.countryDTOS = countryDTOS;
    }


    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        holder.countryText.setText(countryDTOS.get(position).getStrArea());
        Glide.with(holder.itemView.getContext()).load(CountryFlag.getFlagUrl(countryDTOS.get(position).getStrArea())).into(holder.countryImage);
        holder.countryCard.setCardBackgroundColor(RandomColorGenerator.getRandomColor(holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return countryDTOS.size();
    }

    public void setCountryDTOS(List<CountryResponse.CountryDTO> countryDTOS) {
        this.countryDTOS = countryDTOS;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView countryImage;
        TextView countryText;
        CardView countryCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryImage = itemView.findViewById(R.id.categoryImage);
            countryText = itemView.findViewById(R.id.categoryText);
            countryCard = itemView.findViewById(R.id.categoryCard);
        }
    }

}
