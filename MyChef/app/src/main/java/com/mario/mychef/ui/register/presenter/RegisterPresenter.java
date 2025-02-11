package com.mario.mychef.ui.register.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mario.mychef.ui.register.RegisterContract;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private  Context context;

    public RegisterPresenter(RegisterContract.View view, Context context) {
        this.view = view;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        this.context = context;
    }

    @Override
    public void registerUser(String email, String username, String password, String confirmPassword) {
        if(validateData(email,username) && validatePassword(password,confirmPassword)){
            view.showProgress();
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            view.hideProgress();
                            view.onRegisterSuccess(auth.getCurrentUser());
                            user = auth.getCurrentUser();
                            saveLoginStateInSharedPreference();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            view.hideProgress();
                            handleRegistrationError(e.getMessage());
                        }
                    });

        }
    }
    private boolean validateData(String email, String username) {
        if (email.isEmpty() || username.isEmpty()) {
            view.showSnackBarMessage("Email and Username fields are required");
            return false;
        }
        return true;
    }
    private boolean validatePassword(String password, String confirmPassword) {
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            view.showSnackBarMessage("Password fields are required");
            return false;
        } else if (!password.equals(confirmPassword)) {
            view.showSnackBarMessage("Passwords mismatch");
        }
        return true;
    }
    private void handleRegistrationError(String errorMessage){
        switch (errorMessage) {
            case "The email address is badly formatted.":
                view.showEmailError("Invalid Email");
                break;
            case "The given password is invalid. [ Password should be at least 6 characters ]":
                view.showPasswordError("Password should be at least 6 characters");
                break;
            case "The email address is already in use by another account.":
                view.showEmailError("Email already in use");
                break;
            default:
                view.showSnackBarMessage("Registration Failed");
                break;
        }
    }
    private void saveLoginStateInSharedPreference(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyChefPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn",true);
        editor.putString("userId", user.getUid());
        editor.putString("userEmail", user.getEmail());
        editor.apply();
    }
}
