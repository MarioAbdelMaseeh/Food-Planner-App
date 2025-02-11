package com.mario.mychef.ui.splash;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mario.mychef.R;

import java.util.Objects;


public class SplashFragment extends Fragment {
    private static final int SPLASH_TIME_OUT = 3000;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = requireContext().getSharedPreferences("MyChefPrefs",MODE_PRIVATE);
        Log.i("TAG", "onViewCreated: "+ sharedPreferences.getBoolean("isLoggedIn",false) + sharedPreferences.getString("userId",""));
        new Handler().postDelayed(()->{
            if(sharedPreferences.getBoolean("isLoggedIn",false)){
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
            }else{
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment);
            }
        },SPLASH_TIME_OUT);
    }
}