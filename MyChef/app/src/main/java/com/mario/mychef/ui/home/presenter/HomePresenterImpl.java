package com.mario.mychef.ui.home.presenter;

import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.ui.home.views.HomeView;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter{
    private HomeView homeView;
    private MealsRepo mealsRepo;
    private MealsResponse.MealDTO dailyMeal;
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
    public void getDetails() {
        homeView.showDetails(this.dailyMeal);
    }

    @Override
    public void getDailyMeal() {
        Disposable disposable = mealsRepo.getRandomMeal()
                .map(MealsResponse::getMeals).subscribeOn(Schedulers.io())
                .map(mealDTOS -> mealDTOS.get(0)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealDTO -> {
                    homeView.showDailyMeal(mealDTO);
                    dailyMeal = mealDTO;
                        }
                        , throwable -> {
                    homeView. showError(throwable.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void getMealsByCategory(String category) {
       Disposable disposable = mealsRepo.getMealsByCategory(category).subscribeOn(Schedulers.io())
                .map(MealsResponse::getMeals).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(homeView::showRandomCategoryMeals
                        , throwable -> {
                    homeView. showError(throwable.getMessage());
                });
       compositeDisposable.add(disposable);
    }

    @Override
    public void getMealsByArea(String area) {
        Disposable disposable = mealsRepo.getMealsByArea(area).subscribeOn(Schedulers.io())
                .map(MealsResponse::getMeals).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(homeView::showRandomCountryMeals
                        , throwable -> {
                    homeView. showError(throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void disposeCompositeDisposable(){
        compositeDisposable.clear();
    }
}
