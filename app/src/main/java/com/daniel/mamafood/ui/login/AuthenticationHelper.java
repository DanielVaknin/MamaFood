package com.daniel.mamafood.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.google.rpc.context.AttributeContext;

public class AuthenticationHelper {

    private EditText _emailEditText;
    private EditText _passwordEditText;
    private FirebaseAuth mAuth;
    private String _email;
    private String _password;
    private Context _context;
    private View _view;
    final ProgressDialog progressDialog;
    private Snackbar loginSnackbar;

    public AuthenticationHelper(EditText emailEditText, EditText passwordEditText, Context context, View view)
    {
        _emailEditText = emailEditText;
        _passwordEditText = passwordEditText;
        _context = context;
        _view = view;
        _email = emailEditText.getText().toString();
        _password = passwordEditText.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(context, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
    }

    public void Login(int actionNavigation)
    {
        boolean isEmptyEmail = AuthenticationHelper.EnsureNotNull(_emailEditText);
        boolean isEmptyPassword = AuthenticationHelper.EnsureNotNull(_passwordEditText);

        if(isEmptyEmail && isEmptyPassword)
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

    public static boolean ValidateEmailPassword(EditText emailEditText, EditText passwordEditText)
    {
        boolean isInputValid = true;
        String emailAddress = emailEditText.getText().toString().trim();
        if (passwordEditText.getText().toString().length() < 6) {
            passwordEditText.setError("password minimum contain 6 character");
            passwordEditText.requestFocus();
            isInputValid = false;
        }
        if (passwordEditText.getText().toString().equals("")) {
            passwordEditText.setError("please enter password");
            passwordEditText.requestFocus();
            isInputValid = false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            emailEditText.setError("please enter valid email address");
            emailEditText.requestFocus();
            isInputValid = false;
        }
        if (emailEditText.getText().toString().equals("")) {
            emailEditText.setError("please enter email address");
            emailEditText.requestFocus();
            isInputValid = false;
        }
        if (!emailAddress.equals("") &&
                passwordEditText.getText().toString().length() >= 6 &&
                !passwordEditText.getText().toString().trim().equals("")
                && android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
        }
        return isInputValid;
    }

    public static boolean ValidateUserName(EditText userEditText)
    {
        boolean isInputValid = true;
        String userName = userEditText.getText().toString();
        if (userEditText.getText().toString().length() < 3) {
            userEditText.setError("user name minimum contain 3 characters");
            userEditText.requestFocus();
            isInputValid = false;
        }
        if (userEditText.getText().toString().equals("")) {
            userEditText.setError("please enter user name");
            userEditText.requestFocus();
            isInputValid = false;
        }
        return isInputValid;
    }

    public static boolean EnsureNotNull(EditText editText)
    {
        boolean isInputValid = true;
        if (editText.getText().toString().equals("")) {
            editText.setError("please enter value");
            editText.requestFocus();
            isInputValid = false;
        }
        return isInputValid;
    }



}
