package com.mario.mychef.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.firebase.auth.FirebaseUser;

public class SharedPreferenceManager {
    private static final String PREF_NAME = "MyChefPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static SharedPreferenceManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferenceManager(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    public void saveLoginState(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.putString(KEY_USER_ID, firebaseUser.getUid());
            editor.putString(KEY_USER_EMAIL, firebaseUser.getEmail());
            editor.apply();
        }
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public void clearLoginState() {
        editor.clear();
        editor.apply();
    }
}

