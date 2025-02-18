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
import com.mario.mychef.sharedpreference.SharedPreferenceManager;
import com.mario.mychef.ui.profile.ProfileContract;
import com.mario.mychef.ui.profile.presenter.ProfilePresenter;

public class ProfileFragment extends Fragment implements ProfileContract.View {
    private TextView logOut;
    private ProfileContract.Presenter presenter;
    private NavController navController;
    private TextView userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)requireActivity()).showBottomNav(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        presenter = new ProfilePresenter(this, getContext());
        logOut = view.findViewById(R.id.logout);
        userName = view.findViewById(R.id.userName);
        if(SharedPreferenceManager.getInstance(requireContext()).getUserName() != null && !SharedPreferenceManager.getInstance(getContext()).getUserName().isEmpty())
        {
            userName.setText(SharedPreferenceManager.getInstance(getContext()).getUserName());
        }
        if (SharedPreferenceManager.getInstance(requireContext()).isLoggedIn()) {
            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.logOut();

                }
            });
        } else {
            logOut.setText("Sign In");
            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToSignIn();
                }
            });

        }
    }

    @Override
    public void navigateToSignIn() {
        navController.navigate(R.id.action_profileFragment_to_loginFragment);
    }
}