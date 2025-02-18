package com.mario.mychef.ui.home.views;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.mario.mychef.MainActivity;
import com.mario.mychef.R;
import com.mario.mychef.db.MealsLocalDataSourceImpl;
import com.mario.mychef.firedb.FireDataBase;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.models.MealsRepoImpl;
import com.mario.mychef.network.MealsRemoteDataSourceImpl;
import com.mario.mychef.network.NetworkUtils;
import com.mario.mychef.sharedpreference.SharedPreferenceManager;
import com.mario.mychef.ui.RandomCategoryPicker;
import com.mario.mychef.ui.RandomCountryPicker;
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
    private RandomCategoryMealsAdapter randomCategoryMealsAdapter;
    private RandomCountryMealsAdapter randomCountryMealsAdapter;
    private RecyclerView randomCategoryRecycleView;
    private RecyclerView randomCountryRecycleView;
    private TextView randomCategoryMealsText;
    private TextView randomCountryMealsText;
    private HomePresenter homePresenter;
    private ImageView dailyMeal;
    private TextView dailyMealName;
    private CardView cardView;
    private ScrollView scrollView;
    private NetworkUtils networkUtils;
    private LottieAnimationView lottie;

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
        homeRecyclerAdapter = new HomeRecyclerAdapter(new ArrayList<>(), this);
        randomCategoryMealsAdapter = new RandomCategoryMealsAdapter(new ArrayList<>(), this);
        randomCountryMealsAdapter = new RandomCountryMealsAdapter(new ArrayList<>(), this);
        dailyMeal = view.findViewById(R.id.dailyMealImage);
        dailyMealName = view.findViewById(R.id.dailyMealName);
        cardView = view.findViewById(R.id.dailyChosenMealCard);
        scrollView = view.findViewById(R.id.homeScrollView);
        lottie = view.findViewById(R.id.homeLottie);
        randomCategoryRecycleView = view.findViewById(R.id.randomCategoryRecycleView);
        randomCountryRecycleView = view.findViewById(R.id.randomCountryRecycleView);
        randomCategoryMealsText = view.findViewById(R.id.randomCategoryMealsText);
        randomCountryMealsText = view.findViewById(R.id.randomCountryMealsText);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.setHorizontalScrollBarEnabled(false);
        recyclerView.setAdapter(homeRecyclerAdapter);
        randomCategoryRecycleView.setVerticalScrollBarEnabled(false);
        randomCategoryRecycleView.setHorizontalScrollBarEnabled(false);
        randomCategoryRecycleView.setAdapter(randomCategoryMealsAdapter);
        randomCountryRecycleView.setVerticalScrollBarEnabled(false);
        randomCountryRecycleView.setHorizontalScrollBarEnabled(false);
        randomCountryRecycleView.setAdapter(randomCountryMealsAdapter);
        String randomCategory = RandomCategoryPicker.getRandomCategory();
        String randomCountry = RandomCountryPicker.getRandomCountry();
        randomCategoryMealsText.setText(String.format("%s Meals", randomCategory));
        randomCountryMealsText.setText(String.format("%s Meals",randomCountry));
        homePresenter = new HomePresenterImpl(this, MealsRepoImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(this.requireContext())));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePresenter.getDetails();
            }
        });
        if(SharedPreferenceManager.getInstance(requireContext()).isLoggedIn()){
        FireDataBase.getInstance().updateMeals(SharedPreferenceManager.getInstance(requireContext()).getUserId(), this.requireContext());
        }
        networkUtils = new NetworkUtils(requireContext(), new NetworkUtils.NetworkStatusListener() {
            @Override
            public void onNetworkLost() {
                requireActivity().runOnUiThread(()-> {
                    scrollView.setVisibility(View.GONE);
                    lottie.setVisibility(View.VISIBLE);
                });
            }
            @Override
            public void onNetworkAvailable() {
                requireActivity().runOnUiThread(()->{
                    scrollView.setVisibility(View.VISIBLE);
                    lottie.setVisibility(View.GONE);
                });
            }
        });
        if(!NetworkUtils.isConnectedToInternet(requireContext())){
            requireActivity().runOnUiThread(()->scrollView.setVisibility(View.GONE));
            lottie.setVisibility(View.VISIBLE);
        }else {
            homePresenter.getMealsByFirstLetter("m");
            homePresenter.getDailyMeal();
            homePresenter.getMealsByCategory(randomCategory);
            homePresenter.getMealsByArea(randomCountry);
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMeals(List<MealsResponse.MealDTO> meals) {
        homeRecyclerAdapter.setMeals(meals);
        homeRecyclerAdapter.notifyDataSetChanged();
    }
    @Override
    public void showDailyMeal(MealsResponse.MealDTO meal){

        dailyMealName.setText(meal.getStrMeal());
        Glide.with(this).load(meal.getStrMealThumb()).into(dailyMeal);
    }

    @Override
    public void showRandomCategoryMeals(List<MealsResponse.MealDTO> meals) {
        randomCategoryMealsAdapter.setMeals(meals);
        randomCategoryMealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRandomCountryMeals(List<MealsResponse.MealDTO> meals) {
        randomCountryMealsAdapter.setMeals(meals);
        randomCountryMealsAdapter.notifyDataSetChanged();
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
    @Override
    public void onResume() {
        super.onResume();
        networkUtils.register();
    }

    @Override
    public void onPause() {
        super.onPause();
        networkUtils.unregister();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homePresenter.disposeCompositeDisposable();
    }
}