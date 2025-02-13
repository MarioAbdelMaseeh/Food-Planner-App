package com.mario.mychef.ui.search.presenter;

import android.util.Log;

import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.CountryResponse;
import com.mario.mychef.models.IngredientsResponse;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.ui.search.SearchContract;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchContract.SearchPresenter{
    private MealsRepo mealsRepo;
    private SearchContract.SearchView view;
    private List<CategoriesResponse.CategoriesDTO> categoriesDTOList;
    private List<IngredientsResponse.IngredientDTO> ingredientsDTOList;
    private List<CountryResponse.CountryDTO> countryDTOList;

    public SearchPresenterImpl(MealsRepo mealsRepo, SearchContract.SearchView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
    }

    @Override
    public void getCategories() {
         mealsRepo.getCategories()
                .map(CategoriesResponse::getCategories)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(list -> {
                     view.showCategories(list);
                     categoriesDTOList = list;
                     },throwable -> view.showError(throwable.getMessage()));
    }

    @Override
    public void getCountries() {
        mealsRepo.getCountries()
                .map(CountryResponse::getCountries)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list ->{
                    view.showCountries(list);
                    countryDTOList = list;},throwable -> view.showError(throwable.getMessage()));
    }

    @Override
    public void getIngredients() {
        mealsRepo.getIngredients()
                .map(object -> object.getIngredient())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    view.showIngredients(list);
                    ingredientsDTOList = list;},throwable -> view.showError(throwable.getMessage()));
    }

    @Override
    public void getFilteredCategories(String text) {
        Observable.fromIterable(categoriesDTOList)
                .filter(category -> category.getStrCategory().toLowerCase().contains(text.toLowerCase()))
                .toList()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> view.showCategories(list),throwable -> view.showError(throwable.getMessage()));
    }

    @Override
    public void getFilteredIngredients(String text) {
        Observable.fromIterable(ingredientsDTOList)
                .filter(ingredient -> ingredient.getStrIngredient().toLowerCase().contains(text.toLowerCase()))
                .toList()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> view.showIngredients(list),throwable -> view.showError(throwable.getMessage()));
    }

    @Override
    public void getFilteredCountries(String text) {
        Observable.fromIterable(countryDTOList)
                .filter(country -> country.getStrArea().toLowerCase().contains(text.toLowerCase()))
                .toList()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> view.showCountries(list),throwable -> view.showError(throwable.getMessage()));
    }


}
