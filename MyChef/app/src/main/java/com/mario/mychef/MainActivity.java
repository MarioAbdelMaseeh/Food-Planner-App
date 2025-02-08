package com.mario.mychef;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showBottomNav(boolean show){
        Group profileGroup = findViewById(R.id.profileGroup);
        if(show){
            profileGroup.setVisibility(View.VISIBLE);
        }else{
            profileGroup.setVisibility(View.GONE);
        }
    }
}