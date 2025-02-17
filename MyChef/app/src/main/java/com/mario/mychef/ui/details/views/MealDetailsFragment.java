package com.mario.mychef.ui.details.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.mario.mychef.MainActivity;
import com.mario.mychef.R;
import com.mario.mychef.databinding.FragmentMealDetailsBinding;
import com.mario.mychef.db.MealsLocalDataSourceImpl;
import com.mario.mychef.models.MealDataBaseModel;
import com.mario.mychef.models.MealsRepoImpl;
import com.mario.mychef.models.MealsResponse;
import com.mario.mychef.network.MealsRemoteDataSourceImpl;
import com.mario.mychef.sharedpreference.SharedPreferenceManager;
import com.mario.mychef.ui.details.MealsDetailsContract;
import com.mario.mychef.ui.details.presenter.MealsDetailsPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MealDetailsFragment extends Fragment implements MealsDetailsContract.MealsDetailsView {
    FragmentMealDetailsBinding binding;
    MealsResponse.MealDTO meal;
    MealsDetailsContract.MealsDetailsPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)requireActivity()).showBottomNav(false);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMealDetailsBinding.bind(view);
        presenter = new MealsDetailsPresenterImpl(this, MealsRepoImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(this.getContext())));
        MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
        if(args.getMealDTO().getStrYoutube() != null){
            meal = args.getMealDTO();
            showMealDetails(meal);
        }else{
            presenter.getMealDetails(args.getMealId());
        }
        binding.addToFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealDataBaseModel mealDataBaseModel = new MealDataBaseModel(meal.getIdMeal(), SharedPreferenceManager.getInstance(requireContext()).getUserId(),"Fav",meal);
                presenter.addMealToFav(meal);
            }
        });
        binding.addToPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarConstraints.Builder constraintsBuilder = DatePickerUtils.getConstraintsFromToday();
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select a Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setCalendarConstraints(constraintsBuilder.build())
                        .build();

                // Show Date Picker Dialog
                datePicker.show(getParentFragmentManager(), "DATE_PICKER");
                datePicker.addOnPositiveButtonClickListener(selection -> {
                    String selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            .format(new Date(selection));
                    Log.i("TAG", "onClick: " + selectedDate);
                    MealDataBaseModel mealDataBaseModel = new MealDataBaseModel(meal.getIdMeal(), SharedPreferenceManager.getInstance(requireContext()).getUserId(),selectedDate,meal);
                    presenter.addMealToPlan(meal,selectedDate);
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void setMeal(MealsResponse.MealDTO meal) {
        this.meal = meal;
    }

    @Override
    public Context getViewContext() {
        return requireContext();
    }

    private String extractYouTubeVideoId(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|e/|watch\\?feature=player_embedded&v=|watch%3Fv%3D|watch%3Ffeature%3Dplayer_embedded%26v%3D)([a-zA-Z0-9_-]{11})";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
    @SuppressLint("SetJavaScriptEnabled")
    @NonNull
    private WebView getWebView() {
        WebView webView = binding.mealVideo;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false; // Load the URL inside WebView
            }
        });
        return webView;
    }

    @Override
    public void showMealDetails(MealsResponse.MealDTO meal) {
        if(meal != null && meal.getStrYoutube() != null)
        {
            WebView webView = getWebView();
            String videoUrl = Objects.requireNonNull(meal).getStrYoutube();
            String videoId = extractYouTubeVideoId(videoUrl);
            if (videoId != null) {
                String embedUrl = "https://www.youtube.com/embed/" + videoId;
                webView.loadUrl(embedUrl);
            } else {
                webView.loadUrl(videoUrl); // Fallback if extraction fails
            }
            Glide.with(this).load(meal.getStrMealThumb()).into(binding.detailsImg);
            binding.detailsMealName.setText(meal.getStrMeal());
            binding.mealInstructions.setText(meal.getStrInstructions());
            String categoryAndArea = meal.getStrCategory() + " - " + meal.getStrArea();
            binding.categoryAndArea.setText(categoryAndArea);
            binding.detailsRecycleView.setAdapter(new DetailsRecyclerAdapter(meal.getIngredientAndMeasures()));
        }

    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

}
