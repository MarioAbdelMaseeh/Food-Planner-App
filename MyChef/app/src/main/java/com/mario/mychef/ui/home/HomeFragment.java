package com.mario.mychef.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mario.mychef.MainActivity;
import com.mario.mychef.R;
import com.mario.mychef.adapters.HomeRecyclerAdapter;
import com.mario.mychef.models.MealsDTO;
import com.mario.mychef.network.MealsClient;
import com.mario.mychef.network.NetworkCallback;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements NetworkCallback {
    private List<MealsDTO.MealDTO> meals ;
    private MealsClient mealsClient;
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter homeRecyclerAdapter;
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
        meals = new ArrayList<>();
        recyclerView = view.findViewById(R.id.homeFragmentRecycleView);
        mealsClient = MealsClient.getInstance();
        mealsClient.makeNetworkCall(this);

    }

    @Override
    public void onSuccessRequest(List<MealsDTO.MealDTO> meals) {
        this.meals = meals;
        homeRecyclerAdapter = new HomeRecyclerAdapter(meals);
        recyclerView.setAdapter(homeRecyclerAdapter);
    }

    @Override
    public void onFailureRequest(String errMsg) {

    }
}