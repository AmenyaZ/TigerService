package com.example.tigerservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;


import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText UserMail;
    private EditText Password;
    private Button LogIn;
    private TextView Register;
    private TextView About;
    private View mProgressView;
    private FirebaseAuth mAuth;
    private View mLoginFormView;
    private TextView tvLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserMail = (EditText)findViewById(R.id.etEmail);
        Password = (EditText)findViewById(R.id.etPassword);
        LogIn = (Button)findViewById(R.id.btnLogIn);
        Register =(TextView)findViewById(R.id.tvRegister);
        About = (TextView)findViewById(R.id.tvAbout);
        mProgressView = (View)findViewById(R.id.login_progress);
        mLoginFormView = (View)findViewById(R.id.login_form);
        tvLoad = (TextView)findViewById(R.id.tvLoad);

        mAuth = FirebaseAuth.getInstance();



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);

                startActivity(intent);
                //  showProgress(true);
                finish();
                return;
            }
        });
    }


}