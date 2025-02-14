package com.mario.mychef.ui.home.views;

import android.annotation.SuppressLint;
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

import com.google.android.material.snackbar.Snackbar;
import com.mario.mychef.MainActivity;
import com.mario.mychef.R;
import com.mario.mychef.db.MealsLocalDataSourceImpl;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.models.MealsRepoImpl;
import com.mario.mychef.network.MealsRemoteDataSourceImpl;
import com.mario.mychef.ui.home.presenter.HomePresenter;
import com.mario.mychef.ui.home.presenter.HomePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements  HomeRecyclerAdapterHelper , HomeView{
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter homeRecyclerAdapter;
    private HomePresenter homePresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)requireActivity()).showBottomNav(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.homeFragmentRecycleView);
        homeRecyclerAdapter = new HomeRecyclerAdapter(new ArrayList<>(),this);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.setHorizontalScrollBarEnabled(false);
        recyclerView.setAdapter(homeRecyclerAdapter);
        homePresenter = new HomePresenterImpl(this, MealsRepoImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(this.requireContext())));
        homePresenter.getMealsByFirstLetter("m");
        Disposable disposable1 = homePresenter.getCategories().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(list -> {
                                    Log.i("Categories", "onViewCreated: " + list.get(1));
                                        }, throwable -> {
                                    showError(throwable.getMessage());
                                    Log.e("Categories", "onViewCreated: "+throwable.getMessage() );
                                        });

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMeals(List<MealsResponse.MealDTO> meals) {
        homeRecyclerAdapter.setMeals(meals);
        homeRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errMsg) {
        Snackbar.make(requireView(), errMsg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showDetails(MealsResponse.MealDTO meal) {
        Log.i("Meal", "showDetails: " + meal.getIdMeal());
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(meal, meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(action);
    }
}