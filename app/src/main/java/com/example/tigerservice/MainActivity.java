package com.example.tigerservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tigerservice.Viewscreen.HireActivity;

public class MainActivity extends AppCompatActivity {

    private Button Hire;
    private Button Work;
    private TextView wLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Hire = (Button) findViewById(R.id.btHire);
        Work = (Button) findViewById(R.id.btWork);
        wLogin = (TextView)findViewById(R.id.tvLogIn);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HireActivity.class);
                startActivity(intent);
            }
        });
        Work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
        wLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
//        String Text = "Already a user, Login";
//        SpannableString spannableString = new SpannableString(Text);
//        final ClickableSpan clickableSpan1 = new ClickableSpan() {
//            @Override
//            public void onClick(@NonNull View widget) {
//                Toast.makeText(MainActivity.this, "Kindly LogIn now", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//                // showProgress(true);
//                finish();
//                return;
//
//
//            }
//        };
    }
}