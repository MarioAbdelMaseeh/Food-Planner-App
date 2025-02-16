package com.mario.mychef.ui.plan.views;

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
import android.widget.CalendarView;

import com.google.android.material.snackbar.Snackbar;
import com.mario.mychef.R;
import com.mario.mychef.db.MealsLocalDataSourceImpl;
import com.mario.mychef.models.MealsRepoImpl;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.network.MealsRemoteDataSourceImpl;
import com.mario.mychef.ui.plan.PlanContract;
import com.mario.mychef.ui.plan.presenter.PlanPresenterImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class PlanFragment extends Fragment implements PlanContract.PlanView, PlanAdapter.PlanAdapterHelper {
    CalendarView calendarView;
    RecyclerView planRecyclerView;
    PlanContract.PlanPresenter presenter;
    PlanAdapter adapter;
    String selectedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView = view.findViewById(R.id.planCalender);
        planRecyclerView = view.findViewById(R.id.planRecycleView);
        presenter = new PlanPresenterImpl(this, MealsRepoImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(this.getContext())));
        adapter = new PlanAdapter(new ArrayList<>(),this);
        planRecyclerView.setAdapter(adapter);
        presenter.getPlanMeals(getFormattedDateFromCalendarView(calendarView));
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = getSelectedDate(year, month, dayOfMonth);
            presenter.getPlanMeals(selectedDate);
            Log.i("TAG", "onViewCreated: " + selectedDate);
        });


    }

    @NonNull
    private static String getSelectedDate(int year, int month, int dayOfMonth) {
        return String.format(Locale.getDefault(),
                "%02d/%02d/%04d", dayOfMonth, month + 1, year);
    }
    public String getFormattedDateFromCalendarView(CalendarView calendarView) {
        long selectedDateMillis = calendarView.getDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectedDateMillis);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Months are zero-based
        int year = calendar.get(Calendar.YEAR);

        return String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);
    }

    @Override
    public void showMeals(List<MealsResponse.MealDTO> meals) {
        adapter.setMeals(meals);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(planRecyclerView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showDetails(MealsResponse.MealDTO meal) {
        PlanFragmentDirections.ActionPlanFragmentToMealDetailsFragment action = PlanFragmentDirections.actionPlanFragmentToMealDetailsFragment(meal,meal.getIdMeal());
        Navigation.findNavController(planRecyclerView).navigate(action);
    }

    @Override
    public void onDeleteClick(MealsResponse.MealDTO meal) {
        presenter.deleteMeal(meal,selectedDate);
    }
}