package com.mario.mychef;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

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
        background.animate().translationY(-2500).setDuration(1000).setStartDelay(5000);
        logo.animate().translationY(2500).setDuration(1000).setStartDelay(5000);
        appName.animate().translationY(2500).setDuration(1000).setStartDelay(5000);
        lottieAnimationView.animate().translationY(2500).setDuration(1000).setStartDelay(5000);
    }
}