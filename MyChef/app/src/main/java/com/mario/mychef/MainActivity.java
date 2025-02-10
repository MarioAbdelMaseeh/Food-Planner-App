package com.mario.mychef;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ImageView profileImage;
    FragmentContainerView navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileImage = findViewById(R.id.profileImage);
        navHostFragment = findViewById(R.id.nav_host_fragment);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProfilePage(navHostFragment.getFragment().getView());
            }
        });
    }

    private void toProfilePage(View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_homeFragment_to_profileFragment);
        showBottomNav(false);
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