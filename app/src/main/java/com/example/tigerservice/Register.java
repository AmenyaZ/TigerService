package com.example.tigerservice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewPropertyAnimator;
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

public class Register extends AppCompatActivity {

    private EditText FName;
    private EditText SName;
    private EditText Username;
    private EditText Password;
    private EditText Password2;
    private TextView textView;
    private TextView Email;
    private Button Register;
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        textView = (TextView)findViewById(R.id.tvLogIn);
        Register = (Button)findViewById(R.id.btRegister);
        FName = (EditText)findViewById(R.id.etFName);
        SName = (EditText)findViewById(R.id.etSName);
        Username = (EditText)findViewById(R.id.etUsername);
        Password = (EditText)findViewById(R.id.etPassword);
        Password2 = (EditText)findViewById(R.id.etPassword2);
        Email = (TextView)findViewById(R.id.etEmail);
        mProgressView = (View)findViewById(R.id.register_progress);
        mLoginFormView = (View)findViewById(R.id.login_form);
        tvLoad = (TextView)findViewById(R.id.tvLoad);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = Email.getText().toString();
                String pass = Password.getText().toString();
                String pass2 = Password2.getText().toString();
                String sName =SName.getText().toString();
                String fName =FName.getText().toString();
                String username = Username.getText().toString();

                if(fName.isEmpty()){
                    FName.setError("Please Enter your First Name");
                    FName.requestFocus();
                }else if (sName.isEmpty()){
                    SName.setError("Please Enter Your Second Name");
                    SName.requestFocus();
                }
                else if (username.isEmpty()){
                    Username.setError("Please enter Your Username");
                    Username.requestFocus();
                }
                else if (mail.isEmpty()){
                    Email.setError("Please Enter your email Address");
                    Email.requestFocus();

                }
                else if (pass.isEmpty()){
                    Password.setError("Please enter your Password");
                    Password.requestFocus();
                }
                else if(pass2.isEmpty()){
                    Password2.setError("Please confirm Your Password");
                    Password2.requestFocus();
                }
                else if (FName.getText().toString().isEmpty() || Email.getText().toString().isEmpty() ||SName.getText().toString().isEmpty() || Username.getText().toString().isEmpty() || Password.getText().toString().isEmpty() || Password2.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else  if (!(FName.getText().toString().isEmpty() || Email.getText().toString().isEmpty() ||SName.getText().toString().isEmpty() || Username.getText().toString().isEmpty() || Password.getText().toString().isEmpty() || Password2.getText().toString().isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Register.this, "Registration Unsuccessful, Please Retry", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(Register.this, Login.class));
                                showProgress(true);
                                return;
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Register.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        String Text = "Already have an account, LogIn";
        SpannableString spannableString =new SpannableString(Text);
        final ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //Toast.makeText(Register.this, "Kindly LogIn now", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                showProgress(true);
                finish();
                return;
            }
        };
        spannableString.setSpan(clickableSpan1 ,25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        ViewPropertyAnimator viewPropertyAnimator = mLoginFormView.animate().setDuration(shortAnimTime).alpha(
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

        }
}