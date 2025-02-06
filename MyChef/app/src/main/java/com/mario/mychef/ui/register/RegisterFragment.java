package com.mario.mychef.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
        registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())){
                register(emailEditText.getText().toString(),passwordEditText.getText().toString());
                }else {
                    Toast.makeText(getContext(),"Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private void register(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getContext(),"Registration Successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Registration Failed", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
            }
        });
    }
}