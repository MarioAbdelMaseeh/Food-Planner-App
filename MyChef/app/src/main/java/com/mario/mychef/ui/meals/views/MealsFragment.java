package com.mario.mychef.ui.meals.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mario.mychef.R;
import com.mario.mychef.db.MealsLocalDataSourceImpl;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsRepoImpl;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.network.MealsRemoteDataSourceImpl;
import com.mario.mychef.ui.meals.MealsContract;
import com.mario.mychef.ui.meals.presenter.MealsPresenterImpl;

import java.util.ArrayList;
import java.util.List;


public class MealsFragment extends Fragment implements MealsContract.MealsView {
    private TextInputEditText searchEditText;
    private TextView searchType;
    private RecyclerView recyclerView;
    private MealsAdapter mealsAdapter;
    private MealsContract.MealsPresenter mealsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MealsFragmentArgs args = MealsFragmentArgs.fromBundle(getArguments());
        String type = args.getType();
        String name = args.getName();
        searchEditText = view.findViewById(R.id.searchEditText);
        searchEditText.setHint("Search by meal name");
        searchType = view.findViewById(R.id.searchType);
        searchType.setText(name + " Meals :");
        mealsPresenter = new MealsPresenterImpl(MealsRepoImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(this.getContext())), this);
        mealsPresenter.getMeals(type, name);
        recyclerView = view.findViewById(R.id.mealsRecycleView);
        mealsAdapter = new MealsAdapter(new ArrayList<>());
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void showMeals(List<MealsResponse.MealDTO> meals) {
        mealsAdapter.setMeals(meals);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }
}