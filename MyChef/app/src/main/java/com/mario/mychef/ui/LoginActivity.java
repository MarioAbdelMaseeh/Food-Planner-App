package com.mario.mychef.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.mario.mychef.R;

public class LoginActivity extends AppCompatActivity {
    ImageView logo;
    ImageView background;
    TextView appName;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logo = findViewById(R.id.logo);
        background = findViewById(R.id.backgroundSplash);
        appName = findViewById(R.id.splashName);
        lottieAnimationView = findViewById(R.id.lottie);
        background.animate().translationY(-3500).setDuration(1000).setStartDelay(5000);
        logo.animate().translationY(2500).setDuration(1000).setStartDelay(5000);
        appName.animate().translationY(2500).setDuration(1000).setStartDelay(5000);
        lottieAnimationView.animate().translationY(2500).setDuration(1000).setStartDelay(5000);
    }
}