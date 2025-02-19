package com.mario.mychef.ui.meals.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mario.mychef.MainActivity;
import com.mario.mychef.R;
import com.mario.mychef.db.MealsLocalDataSourceImpl;
import com.mario.mychef.models.MealsRepo;
import com.mario.mychef.models.MealsRepoImpl;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.network.MealsRemoteDataSourceImpl;
import com.mario.mychef.network.NetworkUtils;
import com.mario.mychef.ui.home.views.HomeFragmentDirections;
import com.mario.mychef.ui.meals.MealsContract;
import com.mario.mychef.ui.meals.presenter.MealsPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;


public class MealsFragment extends Fragment implements MealsContract.MealsView , MealsAdapterHelper, NetworkUtils.NetworkStatusListener {
    private TextInputEditText searchEditText;
    private TextView searchType;
    private RecyclerView recyclerView;
    private MealsAdapter mealsAdapter;
    private NetworkUtils networkUtils;

    private MealsContract.MealsPresenter mealsPresenter;
    private LottieAnimationView lottieAnimationView;
    private Group group;
    private String type;
    private String name;
    PublishSubject<String> searchSubject = PublishSubject.create();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)requireActivity()).showBottomNav(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MealsFragmentArgs args = MealsFragmentArgs.fromBundle(getArguments());
         type = args.getType();
         name = args.getName();
        searchEditText = view.findViewById(R.id.searchEditText);
        networkUtils = new NetworkUtils(requireContext(),this );
        searchEditText.setHint("Search by meal name");
        searchType = view.findViewById(R.id.searchType);
        lottieAnimationView = view.findViewById(R.id.mealsLottie);
        group = view.findViewById(R.id.mealsGroup);
        searchType.setText(name + " Meals :");
        mealsPresenter = new MealsPresenterImpl(MealsRepoImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(this.getContext())), this);
        if(NetworkUtils.isConnectedToInternet(requireContext())){
            group.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.GONE);
            mealsPresenter.getMeals(type, name);
        }else{
            group.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);
        }
        recyclerView = view.findViewById(R.id.mealsRecycleView);
        mealsAdapter = new MealsAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(mealsAdapter);
        Disposable disposable = searchSubject.debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> mealsPresenter.getMeals("name", text), throwable -> showError(throwable.getMessage()));
        compositeDisposable.add(disposable);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSubject.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void showMeals(List<MealsResponse.MealDTO> meals) {
        mealsAdapter.setMeals(meals);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showDetails(MealsResponse.MealDTO meal, String id) {
        MealsFragmentDirections.ActionMealsFragmentToMealDetailsFragment action = MealsFragmentDirections.actionMealsFragmentToMealDetailsFragment(meal,meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }

    }

    @Override
    public void onNetworkLost() {
        requireActivity().runOnUiThread(()-> {
            lottieAnimationView.setVisibility(View.VISIBLE);
            group.setVisibility(View.GONE);
        });
    }

    @Override
    public void onNetworkAvailable() {
        requireActivity().runOnUiThread(()-> {
            lottieAnimationView.setVisibility(View.GONE);
            group.setVisibility(View.VISIBLE);
        });
        mealsPresenter.getMeals(type, name);
    }

    @Override
    public void onPause() {
        super.onPause();
        networkUtils.unregister();
    }

    @Override
    public void onResume() {
        super.onResume();
        networkUtils.register();

    }
}