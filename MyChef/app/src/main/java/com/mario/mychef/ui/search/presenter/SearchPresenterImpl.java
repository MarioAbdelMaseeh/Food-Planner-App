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
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchContract.SearchPresenter{
    private MealsRepo mealsRepo;
    private SearchContract.SearchView view;
    private List<CategoriesResponse.CategoriesDTO> categoriesDTOList;
    private List<IngredientsResponse.IngredientDTO> ingredientsDTOList;
    private List<CountryResponse.CountryDTO> countryDTOList;
    private Disposable disposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchPresenterImpl(MealsRepo mealsRepo, SearchContract.SearchView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
    }

    @Override
    public void getCategories() {
        disposable = mealsRepo.getCategories()
                .map(CategoriesResponse::getCategories)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(list -> {
                     view.showCategories(list);
                     categoriesDTOList = list;
                     },throwable -> view.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void getCountries() {
        disposable = mealsRepo.getCountries()
                .map(CountryResponse::getCountries)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list ->{
                    view.showCountries(list);
                    countryDTOList = list;},throwable -> view.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void getIngredients() {
        disposable = mealsRepo.getIngredients()
                .map(object -> object.getIngredient())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    view.showIngredients(list);
                    ingredientsDTOList = list;},throwable -> view.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void getFilteredCategories(String text) {
        disposable = Observable.fromIterable(categoriesDTOList)
                .filter(category -> category.getStrCategory().toLowerCase().contains(text.toLowerCase()))
                .toList()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> view.showCategories(list),throwable -> view.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void getFilteredIngredients(String text) {
        disposable = Observable.fromIterable(ingredientsDTOList)
                .filter(ingredient -> ingredient.getStrIngredient().toLowerCase().contains(text.toLowerCase()))
                .toList()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> view.showIngredients(list),throwable -> view.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void getFilteredCountries(String text) {
        disposable = Observable.fromIterable(countryDTOList)
                .filter(country -> country.getStrArea().toLowerCase().contains(text.toLowerCase()))
                .toList()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> view.showCountries(list),throwable -> view.showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public int getCategoriesSize() {
        return (categoriesDTOList == null) ? 0 : categoriesDTOList.size();
    }

    @Override
    public int getIngredientsSize() {
        return (ingredientsDTOList == null) ? 0 : ingredientsDTOList.size();
    }

    @Override
    public int getCountriesSize() {
        return (countryDTOList == null) ? 0 : countryDTOList.size();
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }


}
