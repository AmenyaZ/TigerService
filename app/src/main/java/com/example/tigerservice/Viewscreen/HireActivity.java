package com.example.tigerservice.Viewscreen;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tigerservice.MainActivity;
import com.example.tigerservice.Model.WorkerModel;
import com.example.tigerservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import Adapter.WorkerAdapter;

public class HireActivity extends AppCompatActivity {
    TextView defaultView;
    DatabaseReference mDatabase;
    FirebaseStorage storage;
    RecyclerView recyclerView;
    WorkerAdapter adapter;
    LinearLayoutManager layoutManager;
    List<WorkerModel> WorkerList;
    List<WorkerModel> filteredProductsList;
    EditText searchText;
    ProgressBar circleP_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setTitle("Mechanic List");

        defaultView = findViewById(R.id.defaultView);

        searchText = findViewById(R.id.editTextSearch);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {

//                    productSearch(s.toString());
                    settAdapter(s.toString());
                } else {
                    adapter = new WorkerAdapter(HireActivity.this, WorkerList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        circleP_bar = findViewById(R.id.progressBarCircle);
        //        Obtaining reference to the firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference("Mechanics");

        storage = FirebaseStorage.getInstance();

        WorkerList = new ArrayList<>();
        filteredProductsList = new ArrayList<>();
//        nameList = new ArrayList<>();
        recyclerView = findViewById(R.id.driverRecycler);
        recyclerView.setHasFixedSize(true);

//       Initializing and Setting up of layout Manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        Initializing and Setting up of adapter
        adapter = new WorkerAdapter(HireActivity.this, WorkerList);
        recyclerView.setAdapter(adapter);

        if (isNetworkConnected()) {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    WorkerList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        WorkerModel model = postSnapshot.getValue(WorkerModel.class);
//                        model.setID(postSnapshot.getKey());

//                        if (Integer.valueOf(receivedProduct.getCapacity()) > 0) {

                        WorkerList.add(model);
//                        } else {
//                            StorageReference storeRef = storage.getReferenceFromUrl(receivedProduct.getImage());

//                            storeRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    mDatabase.child(receivedProduct.getID()).removeValue();
//                                }
//                            })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(Products_view.this, "Error in Deletion", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                        }
                    }

                    if (WorkerList.isEmpty()) {
                        defaultView.setVisibility(View.VISIBLE);
                        circleP_bar.setVisibility(View.INVISIBLE);
                    }

                    adapter.notifyDataSetChanged();
                    circleP_bar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(HireActivity.this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(HireActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    circleP_bar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            circleP_bar.setVisibility(View.INVISIBLE);
            defaultView.setVisibility(View.VISIBLE);
            defaultView.setText(R.string.No_network);
            Toast.makeText(this, "Okello Check out", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void settAdapter(final String queryString) {


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                filteredProductsList.clear();
                recyclerView.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String pName = snapshot.child("name").getValue(String.class);
                    String pSpeciality = snapshot.child("speciality").getValue(String.class);
                    String pLocation = snapshot.child("location").getValue(String.class);

//                    Products filteredProduct = snapshot.getValue(Products.class);
                    WorkerModel modelFiltered = snapshot.getValue(WorkerModel.class);

                    if (pName.equals(null)) {
                        Toast.makeText(HireActivity.this, "Name is null", Toast.LENGTH_SHORT).show();
                    } else {
                        if (pName.toLowerCase().contains(queryString.toLowerCase())) {
//                            nameList.add(pName);
                            filteredProductsList.add(modelFiltered);
                        }

                        if (pSpeciality.equals(null)) {
                            Toast.makeText(HireActivity.this, "Specialization is Null", Toast.LENGTH_SHORT).show();
                        } else {
                            if (pSpeciality.toLowerCase().contains(queryString.toLowerCase())) {
                                filteredProductsList.add(modelFiltered);
                            }

                            if (pLocation.equals(null)) {
                                Toast.makeText(HireActivity.this, "Location is null", Toast.LENGTH_SHORT).show();
                            } else {
                                if (pLocation.toLowerCase().contains(queryString.toLowerCase())) {
                                    filteredProductsList.add(modelFiltered);
                                }
                            }
                        }
                    }
                }

                if (!filteredProductsList.isEmpty()) {
                    defaultView.setVisibility(View.INVISIBLE);
                    adapter = new WorkerAdapter(HireActivity.this, filteredProductsList);
                    recyclerView.setAdapter(adapter);
                } else {
                    defaultView.setVisibility(View.VISIBLE);
                    defaultView.setText("No Products based on Your Search Criteria... Try Again");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logOut:
//                logging out implementation
//                signOutmAuth.signOut();
                Intent logIntent = new Intent(this, MainActivity.class);
                startActivity(logIntent);
//                Toast.makeText(this, "Log Out Implementation Coming Soon", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);


        return true;
    }

}
