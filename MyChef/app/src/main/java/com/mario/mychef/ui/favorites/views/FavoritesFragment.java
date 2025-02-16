package com.mario.mychef.ui.favorites.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.mario.mychef.R;
import com.mario.mychef.db.MealsLocalDataSourceImpl;
import com.mario.mychef.models.MealsRepoImpl;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.network.MealsRemoteDataSourceImpl;
import com.mario.mychef.ui.favorites.FavoritesContract;
import com.mario.mychef.ui.favorites.presenter.FavoritesPresenter;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements FavoritesContract.View,FavoritesAdapterListener {
    RecyclerView favoritesRecyclerView;
    FavoritesPresenter presenter;
    FavoritesAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritesRecyclerView = view.findViewById(R.id.favoritesListRecycler);
        presenter = new FavoritesPresenter(this, MealsRepoImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),MealsLocalDataSourceImpl.getInstance(requireContext())));
        presenter.getMeals();
        adapter = new FavoritesAdapter(new ArrayList<>(),this);
        favoritesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showMeals(List<MealsResponse.MealDTO> meals) {
        adapter.setMeals(meals);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(MealsResponse.MealDTO meal) {
        presenter.deleteMeal(meal);
    }

    @Override
    public void onMealClick(MealsResponse.MealDTO mealDTO) {
        FavoritesFragmentDirections.ActionFavoritesFragmentToMealDetailsFragment action = FavoritesFragmentDirections.actionFavoritesFragmentToMealDetailsFragment(mealDTO, mealDTO.getIdMeal());
        Navigation.findNavController(requireView()).navigate(action);

    }
}