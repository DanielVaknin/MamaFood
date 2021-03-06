package com.daniel.mamafood.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.mamafood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class loginFragment extends Fragment {


    private Button signinBtn;
    private FirebaseAuth mAuth;
    Snackbar mySnackbar;



    public loginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        signinBtn = view.findViewById(R.id.signin_button);
        TextView signupLink = view.findViewById(R.id.login_link_signup_textview);

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getContext(), loginFragment.class);
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = view.findViewById(R.id.login_email_edittext);
                EditText passwordEditText = view.findViewById(R.id.login_password_edittext);

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                AuthenticationHelper authenticationHelper = new AuthenticationHelper(email,password,getActivity(),view);
                authenticationHelper.Login(R.id.action_loginFragment_to_nav_meals);
            }
        });
        return view;
    }
}