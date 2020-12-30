package com.example.tigerservice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    private EditText UserMail;
    private EditText Password;
    private Button LogIn;
    private TextView Register;
    private TextView About;
    private View mProgressView;
    private FirebaseAuth mAuth;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStatelistener;
    private View mLoginFormView;
    private TextView tvLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        UserMail = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
        LogIn = (Button) findViewById(R.id.btnLogIn);
        Register = (TextView) findViewById(R.id.tvRegister);
        About = (TextView) findViewById(R.id.tvAbout);
        mProgressView = (View) findViewById(R.id.login_progress);
        mLoginFormView = (View) findViewById(R.id.login_form);
        tvLoad = (TextView) findViewById(R.id.tvLoad);

        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);

                startActivity(intent);
                showProgress(true);
                finish();
                return;
            }
        });

        mAuthStatelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(Login.this, "You are Logged In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, SecondActivity.class);
                } else {

                    Toast.makeText(Login.this, "Please LogIn", Toast.LENGTH_SHORT).show();
                }
            }
        };

        LogIn.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                         String email = UserMail.getText().toString();
                                         String pass = Password.getText().toString();

                                         if (email.isEmpty()) {
                                             UserMail.setError("Please Enter Email Address");
                                         } else if (pass.isEmpty()) {
                                             Password.setError("Please enter the password");
                                         } else if (email.isEmpty() && pass.isEmpty()) {
                                             Toast.makeText(Login.this, "All Fields are Empty", Toast.LENGTH_LONG).show();
                                         } else if (!(email.isEmpty() && pass.isEmpty())) {
                                             mFirebaseAuth.signInWithEmailAndPassword(email, pass)
                                                     .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<AuthResult> task) {
                                                             if (!task.isSuccessful()) {
                                                                 Toast.makeText(Login.this, "LogIn Error, Please Check Your Credentials", Toast.LENGTH_LONG).show();
                                                             } else {
                                                                 Intent intHome = new Intent(Login.this, Categories.class);
                                                                 startActivity(intHome);
                                                                 showProgress(true);
                                                                 finish();
                                                                 return;
                                                             }
                                                         }


                                                     });
                                         }


                                     }


                                 }


        );

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

