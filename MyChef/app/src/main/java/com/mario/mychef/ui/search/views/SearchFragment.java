package com.mario.mychef.ui.search.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mario.mychef.R;
import com.mario.mychef.db.MealsLocalDataSourceImpl;
import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.CountryResponse;
import com.mario.mychef.models.IngredientsResponse;
import com.mario.mychef.models.MealsRepoImpl;
import com.mario.mychef.network.MealsRemoteDataSourceImpl;
import com.mario.mychef.ui.search.SearchContract;
import com.mario.mychef.ui.search.presenter.SearchPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SearchFragment extends Fragment implements SearchContract.SearchView, AdapterHelper {

    private RecyclerView categoryRecyclerView;
    private RecyclerView ingredientRecyclerView;
    private RecyclerView countryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private IngredientsAdapter ingredientsAdapter;
    private CountryAdapter countryAdapter;
    private SearchContract.SearchPresenter searchPresenter;
    private SearchView searchView;
    private PublishSubject<String> searchSubject = PublishSubject.create();
    private Disposable categoryDisposable;
    private boolean isCategory = false;
    private boolean isIngredient = false;
    private boolean isCountry = false;
    private boolean ignore = false;
    Chip chip;

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
        isCategory = false;
        isIngredient = false;
        isCountry = false;

        categoryRecyclerView = view.findViewById(R.id.categoryRecycleView);
        ingredientRecyclerView = view.findViewById(R.id.ingredientRecycleView);
        countryRecyclerView = view.findViewById(R.id.countryRecycleView);
        searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by category");
        searchPresenter = new SearchPresenterImpl(MealsRepoImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(this.requireContext())),this);
        categoryAdapter =new CategoryAdapter(new ArrayList<>(),this);
        ingredientsAdapter = new IngredientsAdapter(new ArrayList<>(),this);
        countryAdapter = new CountryAdapter(new ArrayList<>(),this);
        ingredientRecyclerView.setVisibility(View.GONE);
        countryRecyclerView.setVisibility(View.GONE);
        searchPresenter.getCategories();
        searchPresenter.getIngredients();
        searchPresenter.getCountries();
        chip = view.findViewById(R.id.chip_2);
        ChipGroup chipGroup = view.findViewById(R.id.chipGroup);
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                ignore =true;
                searchView.setQuery("",false);
                ignore=false;
                searchView.clearFocus();
                hideRecyclerViews();
                changeRecyclerViewsState(checkedIds, view);
            }
        });
        categoryDisposable = searchSubject.debounce(500,TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                                .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(text ->{
                                            if(isCategory){
                                                searchPresenter.getFilteredCategories(text);
                                            } else if(isIngredient){
                                                searchPresenter.getFilteredIngredients(text);
                                            } else if (isCountry) {
                                                searchPresenter.getFilteredCountries(text);
                                            }
                                        });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    if(!ignore){
                        searchSubject.onNext(newText);
                    }
                    return false;
                }
            });
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (categoryDisposable != null && !categoryDisposable.isDisposed()) {
            categoryDisposable.dispose();
        }
    }
    @Override
    public void showCategories(List<CategoriesResponse.CategoriesDTO> categories) {
        Log.d("TAG", "Categories received: " + categories.size());
        categoryAdapter.setCategoriesDTOList(categories);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void showIngredients(List<IngredientsResponse.IngredientDTO> ingredients) {
        ingredientsAdapter.setIngredientDTOList(ingredients);
        ingredientRecyclerView.setAdapter(ingredientsAdapter);
    }
    @Override
    public void showCountries(List<CountryResponse.CountryDTO> countries){
        countryAdapter.setCountryDTOS(countries);
        countryRecyclerView.setAdapter(countryAdapter);
    }

    @Override
    public void showError(String message) {
        Log.e("TAG", "showError: "+ message );

    }
    private void changeRecyclerViewsState(@NonNull List<Integer> checkedIds, @NonNull View view) {
        for (int id : checkedIds) {
            Chip chip = view.findViewById(id);
            if (chip.getText().equals("Category")) {
                searchView.setQueryHint("Search by category");
                if(categoryAdapter.getItemCount() != searchPresenter.getCategoriesSize()){
                    searchPresenter.getFilteredCategories("");
                }
                categoryRecyclerView.setVisibility(View.VISIBLE);
                isCategory = true;
            } else if (chip.getText().equals("Country")) {
                searchView.setQueryHint("Search by country");
                if(countryAdapter.getItemCount() != searchPresenter.getCountriesSize()){
                    searchPresenter.getFilteredCountries("");
                }
                countryRecyclerView.setVisibility(View.VISIBLE);
                isCountry = true;
            } else if (chip.getText().equals("Ingredient")) {
                searchView.setQueryHint("Search by ingredient");
                if(ingredientsAdapter.getItemCount() != searchPresenter.getIngredientsSize()){
                    searchPresenter.getFilteredIngredients("");
                }
                ingredientRecyclerView.setVisibility(View.VISIBLE);
                isIngredient = true;
            }
        }
    }

    private void hideRecyclerViews() {
        categoryRecyclerView.setVisibility(View.GONE);
        ingredientRecyclerView.setVisibility(View.GONE);
        countryRecyclerView.setVisibility(View.GONE);
        isCategory = false;
        isIngredient = false;
        isCountry = false;
    }

    @Override
    public void showMeals(String type, String name) {
        //chip.setChecked(true);
        SearchFragmentDirections.ActionSearchFragmentToMealsFragment action = SearchFragmentDirections.actionSearchFragmentToMealsFragment(type,name);
        Navigation.findNavController(requireView()).navigate(action);
    }
}
