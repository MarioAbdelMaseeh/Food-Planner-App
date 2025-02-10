package com.mario.mychef.ui.login.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mario.mychef.ui.login.LoginContract;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private FirebaseAuth auth;
    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        auth = FirebaseAuth.getInstance();
    }
    @Override
    public void loginWithEmail(String email, String password) {
        if (validateData(email, password)) {
            view.showProgress();
            auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(authResult -> {
                        view.showLoginSuccess();
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


}
