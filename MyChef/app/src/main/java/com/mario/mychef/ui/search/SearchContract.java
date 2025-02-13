package com.mario.mychef.ui.search;

import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.IngredientsResponse;

import java.util.List;

public interface SearchContract {
    interface SearchView{
        public void showCategories(List<CategoriesResponse.CategoriesDTO> categories);
        //public void showCountries(List<> countries);
        public void showIngredients(List<IngredientsResponse.IngredientDTO> ingredients);
        public void showError(String message);

    }
    interface SearchPresenter{
        public void getCategories();
        public void getCountries();
        public void getIngredients();
    }
}
