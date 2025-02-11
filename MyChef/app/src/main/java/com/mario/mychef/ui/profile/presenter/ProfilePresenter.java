package com.mario.mychef.ui.profile.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mario.mychef.ui.profile.ProfileContract;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View view;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Context context;

    public ProfilePresenter(ProfileContract.View view, Context context) {
        this.view = view;
        this.context = context;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    @Override
    public void logOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyChefPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        auth.signOut();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN);
        googleSignInClient.signOut();
        view.navigateToSignIn();
        Log.i("LogOut", "logOut: " + sharedPreferences.getBoolean("isLoggedIn", false) + auth.getUid());
    }
}
