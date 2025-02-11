package com.mario.mychef.ui.profile;

public interface ProfileContract {
    interface View{
        void navigateToSignIn();
    }
    interface Presenter{
        void logOut();
    }
}
