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
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.mario.mychef.sharedpreference.SharedPreferenceManager;

public class MainActivity extends AppCompatActivity {
    ImageView profileImage;
    FragmentContainerView navHostFragment;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileImage = findViewById(R.id.profileImage);
        navHostFragment = findViewById(R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProfilePage(navHostFragment.getFragment().getView());
            }
        });
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() != itemId) {
                if (itemId == R.id.homeFragment) {
                    // Clear entire back stack when navigating to Home
                    navController.popBackStack(R.id.homeFragment, true);
                    navController.navigate(R.id.homeFragment);
                } else {
                    // Remove the fragment from the back stack before navigating
                    navController.popBackStack(itemId, true);

                    // Check login for restricted fragments
                    if (itemId == R.id.planFragment || itemId == R.id.favoritesFragment) {
                        if (SharedPreferenceManager.getInstance(this).isLoggedIn()) {
                            navController.navigate(itemId);
                            return true;
                        } else {
                            Snackbar.make(navHostFragment.requireView(), "Please Login First", Snackbar.ANIMATION_MODE_FADE).show();
                            return false;
                        }
                    }

                    // Navigate to selected fragment
                    navController.navigate(itemId);
                }
            }
            return true;
        });


//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() != itemId) {
//                if (itemId == R.id.homeFragment) {
//                    // Pop everything up to homeFragment and make it the only fragment
//                    navController.popBackStack(R.id.homeFragment, true);
//                    navController.navigate(R.id.homeFragment);
//                }else if (itemId == R.id.planFragment) {
//                    if(SharedPreferenceManager.getInstance(this).isLoggedIn()){
//                        navController.popBackStack(itemId, false);
//                        navController.navigate(itemId);
//                        return true;
//                    }else{
//                        Snackbar.make(navHostFragment.requireView(), "Please Login First", Snackbar.ANIMATION_MODE_FADE).show();
//                        return false;
//                    }
//                } else if (itemId == R.id.favoritesFragment) {
//                    if(SharedPreferenceManager.getInstance(this).isLoggedIn()){
//                        navController.popBackStack(itemId, false);
//                        navController.navigate(itemId);
//                        return true;
//                    }else{
//                        Snackbar.make(navHostFragment.requireView(), "Please Login First", Snackbar.ANIMATION_MODE_FADE).show();
//                        return false;
//                    }
//                } else {
//                    navController.popBackStack(itemId, false);
//                    navController.navigate(itemId);
//                }
//            }
//            return true;
//        });
        bottomNavigationView.setOnItemReselectedListener(item -> {
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