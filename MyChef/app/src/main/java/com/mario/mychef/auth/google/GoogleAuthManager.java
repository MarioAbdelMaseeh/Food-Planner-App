package com.mario.mychef.auth.google;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.mario.mychef.R;

public class GoogleAuthManager {
    private final GoogleSignInClient googleSignInClient;
    private final GoogleAuthCallback googleAuthCallback;
    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 123;

    public GoogleAuthManager(Context context, GoogleAuthCallback callback) {
           this.googleAuthCallback = callback;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(context,gso);
    }
    public void launchGoogleIntent(Fragment fragment){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        fragment.startActivityForResult(signInIntent,GOOGLE_SIGN_IN_REQUEST_CODE);
    }
    public void handleActivityResult(int requestCode,int resultCode, Intent data){
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    task.addOnCompleteListener(completedTask -> {
                        if (completedTask.isSuccessful()) {
                            GoogleSignInAccount account = completedTask.getResult();
                            if (account != null) {
                                googleAuthCallback.onGoogleSignInSuccess(account.getIdToken());
                            } else {
                                googleAuthCallback.onGoogleSignInFailed("Google Sign In Failed: Account is null");
                            }
                        } else {
                            Exception exception = completedTask.getException();
                            if (exception instanceof ApiException) {
                                ApiException apiException = (ApiException) exception;
                                googleAuthCallback.onGoogleSignInFailed("Google Sign In Failed: " + apiException.getStatusCode());
                                Log.e("GoogleAuthManager", "Google Sign In Failed: " + apiException.getStatusCode(), apiException);
                            } else {
                                googleAuthCallback.onGoogleSignInFailed("Google Sign In Failed: " + exception.getMessage());
                                Log.e("GoogleAuthManager", "Google Sign In Failed", exception);
                            }
                        }
                    });
                } else {
                    googleAuthCallback.onGoogleSignInFailed("Google Sign In Failed: Data is null");
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                googleAuthCallback.onGoogleSignInFailed("Google Sign In Canceled");
            } else {
                googleAuthCallback.onGoogleSignInFailed("Google Sign In Failed: Unknown result code");
            }
        }
    }
}
