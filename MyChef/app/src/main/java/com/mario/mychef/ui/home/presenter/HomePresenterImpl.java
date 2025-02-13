package com.mario.mychef.ui.home.presenter;

import com.mario.mychef.models.CategoriesResponse;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.ui.home.views.HomeView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter{
    private HomeView homeView;
    private MealsRepo mealsRepo;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomePresenterImpl(HomeView homeView, MealsRepo mealsRepo) {
        this.homeView = homeView;
        this.mealsRepo = mealsRepo;
    }

    @Override
    public void getMealsByFirstLetter(String firstLetter) {
        Disposable disposable = mealsRepo.getMealsByFirstLetter(firstLetter)
               .map(MealsResponse::getMeals).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(homeView::showMeals
                        , throwable -> {
                   homeView. showError(throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }
    @Override
    public Single<List<CategoriesResponse.CategoriesDTO>> getCategories(){
        return mealsRepo.getCategories()
                .map(object -> object.getCategories());
    }
    @Override
    public void addMealToPlan(MealsResponse.MealDTO meal) {

    }
    public void disposeCompositeDisposable(){
        compositeDisposable.dispose();
    }
}
