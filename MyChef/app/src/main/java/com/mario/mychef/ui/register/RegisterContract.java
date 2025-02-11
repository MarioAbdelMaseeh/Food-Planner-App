package com.mario.mychef.ui.register;

import com.google.firebase.auth.FirebaseUser;

public interface RegisterContract {
    interface View {
        void showProgress();
        void hideProgress();
        void onRegisterSuccess(FirebaseUser user);
        void onRegisterFailure(String errorMessage);
        void showEmailError(String message);
        void showPasswordError(String message);
        void showSnackBarMessage(String message);
    }
    interface Presenter {
        void registerUser(String email,String username, String password, String confirmPassword);
    }
}
