package com.mario.mychef.ui;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.mario.mychef.R;

public class AuthWithGoogle {
    private GoogleSignInClient mGoogleSignInClient;
    private static AuthWithGoogle instance;

    private AuthWithGoogle(Context context, String idToken) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(idToken).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(context,gso);
    }
    public AuthWithGoogle getInstance(Context context, String idToken){
        if (instance == null){
            instance = new AuthWithGoogle(context,idToken);
        }
        return instance;
    }
}
