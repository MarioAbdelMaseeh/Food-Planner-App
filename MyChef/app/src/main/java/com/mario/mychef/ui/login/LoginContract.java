package com.mario.mychef.ui.login;

public interface LoginContract {

    interface View {
        void showProgress();
        void hideProgress();
        void showLoginSuccess();
        void showLoginError(String message);
        void showGoogleSignInSuccess();
        void showGoogleSignInError(String message);
    }
    interface Presenter {
        void loginWithEmail(String email, String password);

        void handleGoogleSignIn(String idToken);

    }
}
