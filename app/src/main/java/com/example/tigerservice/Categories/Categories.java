package com.example.tigerservice.Categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tigerservice.R;

public class Categories extends AppCompatActivity {

    private Button Plumber;
    private Button Painter;
    private Button Fumigator;
    private Button Mason;
    private Button Electrician;
    private Button Tailor;
    private Button Gardener;
    private Button Others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Plumber = (Button)findViewById(R.id.btPlumber);
        Painter = (Button)findViewById(R.id.btPainter);
        Fumigator = (Button)findViewById(R.id.btFumigator);
        Mason = (Button)findViewById(R.id.btMason);
        Electrician = (Button)findViewById(R.id.btElectrcian);
        Tailor = (Button)findViewById(R.id.btTailor);
        Gardener = (Button)findViewById(R.id.btGardener);
        Others = (Button)findViewById(R.id.btOthers );

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, MyPlumber.class);
                startActivity(intent);
            }
        });

        Painter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, MyPainter.class);
                startActivity(intent);
            }
        });

        Fumigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, MyFumigator.class);
                startActivity(intent);
            }
        });

        Mason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, MyMason.class);
                startActivity(intent);
            }
        });

        Electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, MyElectrician.class);
                startActivity(intent);
            }
        });

        Tailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, MyTailor.class);
                startActivity(intent);
            }
        });

        Gardener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, MyGardener.class);
                startActivity(intent);
            }
        });

        Others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, OtherCat.class);
                startActivity(intent);
            }
        });

    }
}