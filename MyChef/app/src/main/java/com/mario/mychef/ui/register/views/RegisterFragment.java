package com.mario.mychef.ui.register.views;

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

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.mario.mychef.R;
import com.mario.mychef.ui.register.RegisterContract;
import com.mario.mychef.ui.register.presenter.RegisterPresenter;

public class RegisterFragment extends Fragment implements RegisterContract.View {
    private RegisterContract.Presenter presenter;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;

    private TextInputEditText usernameEditText;
    Button registerButton;
    private TextView signInTxt;
    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new RegisterPresenter(this);
        emailEditText = view.findViewById(R.id.emailTextFieldEditText);
        passwordEditText = view.findViewById(R.id.passwordTextFieldEditText);
        usernameEditText = view.findViewById(R.id.usernameTextFieldEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordTextFieldEditText);
        signInTxt = view.findViewById(R.id.sign_in_text);
        registerButton = view.findViewById(R.id.register_button);
        progressBar = view.findViewById(R.id.progressBar);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.registerUser(emailEditText.getText().toString()
                        ,usernameEditText.getText().toString()
                        ,passwordEditText.getText().toString()
                        ,confirmPasswordEditText.getText().toString());
            }
        });
        signInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        registerButton.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        registerButton.setEnabled(true);
    }

    @Override
    public void onRegisterSuccess(FirebaseUser user) {
        Snackbar.make(requireView(), "Registration Successful", Snackbar.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_registerFragment_to_homeFragment);
    }

    @Override
    public void onRegisterFailure(String errorMessage) {
        Snackbar.make(requireView(), "Registration Failed: " + errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showEmailError(String message) {
        emailEditText.setError(message);
    }

    @Override
    public void showPasswordError(String message) {
        passwordEditText.setError(message);
    }

    @Override
    public void showSnackBarMessage(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }
}