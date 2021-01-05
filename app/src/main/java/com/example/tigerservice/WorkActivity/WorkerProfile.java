package com.example.tigerservice.WorkActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tigerservice.Model.WorkerModel;
import com.example.tigerservice.R;
import com.example.tigerservice.Viewscreen.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class WorkerProfile extends AppCompatActivity {

    EditText name, phone, location, email;
    Uri image_uri;
    Button uploadButton ;
    ImageView workImage;
    String nameWork, phoneWork, locationWork, emailWork, spec;
    FirebaseDatabase database ;
    DatabaseReference mechanicReference ;
    WorkerModel worker ;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressBar mProgress;
    public static final int IMAGE_REQUEST = 1;
    private static final int PERMISSION_CODE = 1000;
    private AutoCompleteTextView autoCompleteTextView;

    private static final int CAMERA_REQUEST_CODE =1;

   // private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setTitle("New Mechanic");

//        Drop down list of mechanics
        String [] specification = new String[] {"Service Technicians", "Diagnostic Technicians", "Brake and Transmission Technicians", "Body Repair Technicians",
                "Vehicle Refinishers", "Vehicle Inspectors", "General"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown, specification);

        autoCompleteTextView = findViewById(R.id.specList);
        autoCompleteTextView.setAdapter(adapter);

        storageReference = FirebaseStorage.getInstance().getReference("Mechanics");
        databaseReference = FirebaseDatabase.getInstance().getReference("Mechanics");
//        database = FirebaseDatabase.getInstance();
//        mechanicReference = database.getReference("Mechanics");
        name = findViewById(R.id.etWorkUserName);
        phone = findViewById(R.id.etWorkPhone);
        location = findViewById(R.id.etWorkLocation);
        email = findViewById(R.id.etWorkMail);
        uploadButton = findViewById(R.id.uploadDBtn);
        workImage = findViewById(R.id.imageViewWorker);
//        imageBtn = findViewById(R.id.imageDBtn);
        mProgress = findViewById(R.id.progressBar);


        worker =new WorkerModel();


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                receiveEntries();
            }
        });


    }

    private void uploadDetails() {
        if (image_uri != null) {
            uploadInfo();
        } else {
            Toast.makeText(this, "Empty Uri...Select an Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadInfo() {


        StorageReference photoReference = storageReference.child(System.currentTimeMillis() + "."
                + getFileExtension(image_uri));

        UploadTask uploadTask = photoReference.putFile(image_uri);

        Toast.makeText(this, "UP " + uploadTask, Toast.LENGTH_SHORT).show();

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                // Handle unsuccessful uploads
                Toast.makeText(WorkerProfile.this, "Fail...", Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(WorkerProfile.this, "Success...", Toast.LENGTH_LONG).show();

                if (taskSnapshot.getMetadata() != null) {
                    if (taskSnapshot.getMetadata().getReference() != null) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(WorkerProfile.this, "Proceed...", Toast.LENGTH_LONG).show();
                                String sImage = uri.toString();

                                mProgress.setVisibility(View.VISIBLE);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgress.setProgress(0);
                                    }
                                }, 500);
                                Toast.makeText(WorkerProfile.this, "Upload Successful..." + sImage, Toast.LENGTH_SHORT).show();

                                worker = new WorkerModel(nameWork, phoneWork, locationWork, emailWork, sImage, spec);
                                String key = databaseReference.push().getKey();
                                worker.setId(key);
                                databaseReference.child(key).setValue(worker);

                                Toast.makeText(WorkerProfile.this, "Success Key retention...", Toast.LENGTH_LONG).show();
                                mProgress.setVisibility(View.INVISIBLE);
                                backToProfile(nameWork, phoneWork,locationWork, emailWork, sImage, spec);
                                name.setText("");
                                location.setText("");
                                phone.setText("");
                                email.setText("");
                                Picasso.get().load("null").placeholder(R.drawable.ic_image_black_24dp).into(workImage);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mProgress.setVisibility(View.INVISIBLE);
                                Toast.makeText(WorkerProfile.this, "Database Fail...", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                mProgress.setProgress((int) progress);
            }
        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String extension = mime.getExtensionFromMimeType(contentResolver.getType(uri));
        return extension;
    }

    private void backToProfile(String nameWork, String phoneWork, String locationWork, String emailWork, String imageWork, String special) {
        Intent backIntent = new Intent(this, Profile.class);
        backIntent.putExtra("phone", phoneWork);
        backIntent.putExtra("name", nameWork);
        backIntent.putExtra("location", locationWork);
//        backIntent.putExtra("price", pro;
//        backIntent.putExtra("capacity", product.getCapacity());
        backIntent.putExtra("mail", emailWork);
        backIntent.putExtra("image", imageWork);
        backIntent.putExtra("speciality", special);
//        backIntent.putExtra("key", key);
        Toast.makeText(this, "Successful Upload of Details..", Toast.LENGTH_SHORT).show();
//
        startActivity(backIntent);
//        Toast.makeText(this, "Upload Done Go to Profile", Toast.LENGTH_SHORT).show();
    }


    private void receiveEntries() {
        nameWork = name.getText().toString().trim();
        phoneWork = phone.getText().toString().trim();
        locationWork = location.getText().toString().trim();
        emailWork = email.getText().toString().trim();
        spec = autoCompleteTextView.getText().toString().trim();

        checkFields();
    }

    private void checkFields() {
        if (nameWork.isEmpty() || phoneWork.isEmpty() || locationWork.isEmpty() || emailWork.isEmpty() || image_uri == null
                || spec.isEmpty()) {
            Toast.makeText(this, "Missing Fields...", Toast.LENGTH_SHORT).show();
        } else {

            if ((phoneWork.length()) < 10) {
                Toast.makeText(this, "Please Enter a Valid Phone Number", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    if (Integer.valueOf(emailMech) ) {
//                        Toast.makeText(this, "Please Enter a Valid Capacity", Toast.LENGTH_SHORT).show();
            }
            else {

                if (isNetworkConnected()) {
                    uploadDetails();
                }

                else {
                    Toast.makeText(WorkerProfile.this, R.string.No_network, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    private void choosingPhoto() {
        Intent chooseIntent = new Intent();
        chooseIntent.setType("image/*");
        chooseIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(chooseIntent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_REQUEST) {

                image_uri = data.getData();
                Picasso.get().load(image_uri).into(workImage);

            }
        }
    }

    public void selectPhoto(View view) {
        choosingPhoto();
    }
}

