package com.daniel.mamafood.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.daniel.mamafood.MainActivity;
import com.daniel.mamafood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import android.content.Context;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationHelper {

    private FirebaseAuth mAuth;
    private String _email;
    private String _password;
    private Context _context;
    private View _view;
    final ProgressDialog progressDialog;
    private Snackbar loginSnackbar;

    public AuthenticationHelper(String email, String password, Context context, View view)
    {
        _email = email;
        _password = password;
        _context = context;
        _view = view;

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(context, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
    }

    public void Login(int actionNavigation)
    {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(_email, _password)
                .addOnCompleteListener((Activity) _context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Navigation.findNavController(_view).navigate(actionNavigation);
                            ((MainActivity)_context).updateUserInfoInDrawer();
                            progressDialog.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(_context, "Authentication failed. " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            loginSnackbar = Snackbar.make(_view, "Failed Login", 20);
                            progressDialog.dismiss();
                        }
                    }
                });
    }


}
