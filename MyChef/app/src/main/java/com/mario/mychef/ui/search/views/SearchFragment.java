package com.mario.mychef.ui.search.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mario.mychef.R;
import com.mario.mychef.databinding.FragmentSearchBinding;
import com.mario.mychef.db.MealsLocalDataSourceImpl;
import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.IngredientsResponse;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsRepoImpl;
import com.mario.mychef.network.MealsRemoteDataSourceImpl;
import com.mario.mychef.ui.search.SearchContract;
import com.mario.mychef.ui.search.presenter.SearchPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchContract.SearchView {

    RecyclerView CategoryRecyclerView;
    RecyclerView IngredientRecyclerView;
    FragmentSearchBinding binding;
    CategoryAdapter categoryAdapter;
    IngredientsAdapter ingredientsAdapter;
    SearchContract.SearchPresenter searchPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CategoryRecyclerView = view.findViewById(R.id.categoryRecycleView);
        IngredientRecyclerView = view.findViewById(R.id.ingredientRecycleView);
        searchPresenter = new SearchPresenterImpl(MealsRepoImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(this.requireContext())),this);
        categoryAdapter =new CategoryAdapter(new ArrayList<>());
        ingredientsAdapter = new IngredientsAdapter(new ArrayList<>());
        ChipGroup chipGroup = view.findViewById(R.id.chipGroup);
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                for (int id : checkedIds) {
                    Chip chip = view.findViewById(id);
                    if (chip.getText().equals("Category")) {
                        searchPresenter.getCategories();
                    } else if (chip.getText().equals("Country")) {

                    } else if (chip.getText().equals("Ingredient")) {
                        searchPresenter.getIngredients();
                    }
                }
            }
        });

        }

    @Override
    public void showCategories(List<CategoriesResponse.CategoriesDTO> categories) {
        categoryAdapter.setCategoriesDTOList(categories);
        CategoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void showIngredients(List<IngredientsResponse.IngredientDTO> ingredients) {
        for (IngredientsResponse.IngredientDTO ingredient : ingredients) {
            System.out.println(ingredient.getStrIngredient());
        }
        ingredientsAdapter.setIngredientDTOList(ingredients);
        IngredientRecyclerView.setAdapter(ingredientsAdapter);
    }

    @Override
    public void showError(String message) {
        Log.e("TAG", "showError: "+ message );

    }
}
