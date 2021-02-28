package com.daniel.mamafood.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniel.mamafood.R;
import com.google.android.material.snackbar.Snackbar;

public class loginFragment extends Fragment {


    private View signinBtn;

    public loginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        signinBtn = view.findViewById(R.id.signin_button);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar mySnackbar = Snackbar.make(view, "Testing Button", 15);
                mySnackbar.show();
            }
        });
        
        return view;
    }
}