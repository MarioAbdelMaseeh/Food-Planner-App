package com.mario.mychef.ui.home.views;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mario.mychef.MainActivity;
import com.mario.mychef.R;
import com.mario.mychef.models.MealsDTO;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements  HomeRecyclerAdapterHelper , HomeView {
    private List<MealsDTO.MealDTO> meals ;
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
        homeRecyclerAdapter = new HomeRecyclerAdapter(meals);
        recyclerView.setAdapter(homeRecyclerAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMeals(List<MealsDTO.MealDTO> meals) {
        homeRecyclerAdapter.setMeals(meals);
        homeRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errMsg) {
        Toast.makeText(getContext(),errMsg, Toast.LENGTH_SHORT).show();
    }
}