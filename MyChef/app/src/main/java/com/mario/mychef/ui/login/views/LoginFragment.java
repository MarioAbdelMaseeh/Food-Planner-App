package com.mario.mychef.ui.login.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mario.mychef.MainActivity;
import com.mario.mychef.R;
import com.mario.mychef.auth.google.GoogleAuthCallback;
import com.mario.mychef.auth.google.GoogleAuthManager;
import com.mario.mychef.ui.login.LoginContract;
import com.mario.mychef.ui.login.presenter.LoginPresenter;


public class LoginFragment extends Fragment implements LoginContract.View, GoogleAuthCallback {
    private TextView register;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;
    private FloatingActionButton googleButton;
    private FloatingActionButton facebookButton;
    private GoogleAuthManager googleAuthManager;
    private LoginPresenter loginPresenter;
    private NavController navController;
    private ProgressBar progressBar;
    private Button skipButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)requireActivity()).showBottomNav(false);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        register = view.findViewById(R.id.register_text);
        emailEditText = view.findViewById(R.id.loginEmailAddressTextField);
        passwordEditText = view.findViewById(R.id.loginPasswordTextField);
        loginButton = view.findViewById(R.id.login_button);
        googleButton = view.findViewById(R.id.google_action_btn);
        facebookButton = view.findViewById(R.id.facebook_action_btn);
        progressBar = view.findViewById(R.id.loginProgressBar);
        skipButton = view.findViewById(R.id.skip_button);
        navController = Navigation.findNavController(view);
        loginPresenter = new LoginPresenter(this, getContext());
        googleAuthManager = new GoogleAuthManager(requireContext(), this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.loginWithEmail(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleAuthManager.launchGoogleIntent(LoginFragment.this);
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_homeFragment);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuthManager.handleActivityResult(requestCode,resultCode ,data);
    }
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        loginButton.setEnabled(true);
    }

    @Override
    public void showLoginSuccess() {
        Snackbar.make(requireView(), "Login Successful", Snackbar.LENGTH_SHORT).show();
        navController.navigate(R.id.action_loginFragment_to_homeFragment);
    }

    @Override
    public void showLoginError(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
        hideProgress();
    }

    @Override
    public void showGoogleSignInSuccess() {
        Snackbar.make(requireView(), "Google Sign In Successful", Snackbar.LENGTH_SHORT).show();
        navController.navigate(R.id.action_loginFragment_to_homeFragment);
    }

    @Override
    public void showGoogleSignInError(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
        hideProgress();
    }

    @Override
    public void onGoogleSignInSuccess(String idToken) {
        loginPresenter.handleGoogleSignIn(idToken);
    }

    @Override
    public void onGoogleSignInFailed(String errorMessage) {
        Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_SHORT).show();
    }
}