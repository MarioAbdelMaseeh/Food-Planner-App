package com.mario.mychef.ui.login.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mario.mychef.ui.login.LoginContract;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private Context context;
    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        this.context = context;
    }
    @Override
    public void loginWithEmail(String email, String password) {
        if (validateData(email, password)) {
            view.showProgress();
            auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(authResult -> {
                        view.showLoginSuccess();
                        firebaseUser = auth.getCurrentUser();
                        saveLoginStateInSharedPreference();
                    }).addOnFailureListener(e -> {
                        view.showLoginError(e.getMessage());
                    });
        }
    }
    @Override
    public void handleGoogleSignIn(String idToken) {
        auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken,null))
                .addOnSuccessListener(authResult -> {
                    view.showGoogleSignInSuccess();
                }).addOnFailureListener(e -> {
                    view.showGoogleSignInError(e.getMessage());
                });
    }
    private boolean validateData(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showLoginError("Email and Password fields are required");
            return false;
        }
        return true;
    }
    private void saveLoginStateInSharedPreference(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyChefPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn",true);
        editor.putString("userId",firebaseUser.getUid());
        editor.putString("userEmail",firebaseUser.getEmail());
        editor.apply();
    }


}
