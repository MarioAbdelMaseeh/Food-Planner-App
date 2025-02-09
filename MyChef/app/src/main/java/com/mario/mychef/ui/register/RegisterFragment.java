package com.mario.mychef.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mario.mychef.R;

public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;

    private TextInputEditText usernameEditText;
    Button registerButton;
    private TextView signInTxt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        emailEditText = view.findViewById(R.id.emailTextFieldEditText);
        passwordEditText = view.findViewById(R.id.passwordTextFieldEditText);
        usernameEditText = view.findViewById(R.id.usernameTextFieldEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordTextFieldEditText);
        signInTxt = view.findViewById(R.id.sign_in_text);
        registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())){
                register(emailEditText.getText().toString(),passwordEditText.getText().toString());
                }else {
                    Snackbar.make(v,"Passwords do not match",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        signInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
    }
    private void register(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getContext(),"Registration Successful", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.action_registerFragment_to_homeFragment);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e.getMessage().equals("The email address is badly formatted.")){
                    emailEditText.setError("Invalid Email");
                }else if(e.getMessage().equals("The given password is invalid. [ Password should be at least 6 characters ]")){
                    passwordEditText.setError("Password should be at least 6 characters");
                }else if(e.getMessage().equals("The email address is already in use by another account.")){
                    emailEditText.setError("Email already in use");
                }else {
                    Snackbar.make(requireView(),"Registration Failed",Snackbar.LENGTH_SHORT).show();
                }
                mAuth.signOut();
            }
        });
    }
    private boolean validateData(String email, String username){
        if(email.isEmpty() || username.isEmpty()){
            Snackbar.make(requireView(),"Email and Username fields are required",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validatePassword(String password , String confirmPassword){
        if(password.isEmpty() || confirmPassword.isEmpty()){
            Snackbar.make(requireView(),"Password fields are required",Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(!password.equals(confirmPassword)){
            Snackbar.make(requireView(),"Passwords do not match",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}