package com.mario.mychef.ui.profile.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mario.mychef.MainActivity;
import com.mario.mychef.R;
import com.mario.mychef.ui.profile.ProfileContract;
import com.mario.mychef.ui.profile.presenter.ProfilePresenter;

public class ProfileFragment extends Fragment implements ProfileContract.View {
    private TextView logOut;
    private ProfileContract.Presenter presenter;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        presenter = new ProfilePresenter(this,getContext());
        logOut = view.findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.logOut();

            }
        });
    }

    @Override
    public void navigateToSignIn() {
        navController.navigate(R.id.action_profileFragment_to_loginFragment);
    }
}