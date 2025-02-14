package com.mario.mychef.ui.search;

import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.CountryResponse;
import com.mario.mychef.models.IngredientsResponse;

import java.util.List;

public interface SearchContract {
    interface SearchView{
        public void showCategories(List<CategoriesResponse.CategoriesDTO> categories);
        //public void showCountries(List<> countries);
        public void showIngredients(List<IngredientsResponse.IngredientDTO> ingredients);
        public void showCountries(List<CountryResponse.CountryDTO> countries);
        public void showError(String message);

    }
    interface SearchPresenter{
        public void getCategories();
        public void getCountries();
        public void getIngredients();
        public void getFilteredCategories(String text);

        public void getFilteredIngredients(String text);

        public void getFilteredCountries(String text);
        public int getCategoriesSize();
        public int getIngredientsSize();
        public int getCountriesSize();
        public void dispose();
    }

}

