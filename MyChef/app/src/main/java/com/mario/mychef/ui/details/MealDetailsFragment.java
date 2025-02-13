package com.mario.mychef.ui.details;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.mario.mychef.MainActivity;
import com.mario.mychef.R;
import com.mario.mychef.databinding.FragmentMealDetailsBinding;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MealDetailsFragment extends Fragment {
    FragmentMealDetailsBinding binding;

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
        MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
        WebView webView = getWebView();
        String videoUrl = Objects.requireNonNull(args.getMealDTO()).getStrYoutube();
        String videoId = extractYouTubeVideoId(videoUrl);
        if (videoId != null) {
            String embedUrl = "https://www.youtube.com/embed/" + videoId;
            webView.loadUrl(embedUrl);
        } else {
            webView.loadUrl(videoUrl); // Fallback if extraction fails
        }
        Glide.with(this).load(args.getMealDTO().getStrMealThumb()).into(binding.detailsImg);
        binding.detailsMealName.setText(args.getMealDTO().getStrMeal());
        binding.mealInstructions.setText(args.getMealDTO().getStrInstructions());
        String categoryAndArea = args.getMealDTO().getStrCategory() + " - " + args.getMealDTO().getStrArea();
        binding.categoryAndArea.setText(categoryAndArea);
        binding.detailsRecycleView.setAdapter(new DetailsRecyclerAdapter(args.getMealDTO().getIngredientAndMeasures()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private String extractYouTubeVideoId(String url) {
        String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|%2Fv%2F|youtu.be%2F|%2Fe%2F|watch%3Fv%3D|%2Fvideos%2F|embed%2F|%2Fv%2F|youtu.be%2F|%2Fe%2F|watch\\?v%3D|watch%3Ffeature%3Dplayer_embedded%26v%3D)([a-zA-Z0-9_-]{11})";
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
}