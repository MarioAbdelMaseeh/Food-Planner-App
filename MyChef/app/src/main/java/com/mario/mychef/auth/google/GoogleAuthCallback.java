package com.mario.mychef.auth.google;

public interface GoogleAuthCallback {
    void onGoogleSignInSuccess(String idToken);
    void onGoogleSignInFailed(String errorMessage);
}
