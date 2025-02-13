package com.mario.mychef.ui.search.presenter;

import android.util.Log;

import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.CountryResponse;
import com.mario.mychef.models.IngredientsResponse;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.ui.search.SearchContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchContract.SearchPresenter{
    private MealsRepo mealsRepo;
    private SearchContract.SearchView view;
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
                 .subscribe(list -> view.showCategories(list),throwable -> view.showError(throwable.getMessage()));
    }

    @Override
    public void getCountries() {
        mealsRepo.getCountries()
                .map(CountryResponse::getCountries)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list ->view.showCountries(list),throwable -> view.showError(throwable.getMessage()));
    }

    @Override
    public void getIngredients() {
        mealsRepo.getIngredients()
                .map(object -> object.getIngredient())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> view.showIngredients(list),throwable -> view.showError(throwable.getMessage()));
    }
}
