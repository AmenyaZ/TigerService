package com.example.tigerservice.WorkActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tigerservice.R;

public class WorkerProfile extends AppCompatActivity {

    private Button mUpload;
    private ImageView mImage;

    private static final int CAMERA_REQUEST_CODE =1;

   // private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);

        //mStorage =FirebaseStorage.getInstance().getReference();

        mUpload = (Button)findViewById(R.id.btUpload);
        mImage = (ImageView)findViewById(R.id.ivImage);

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE  && resultCode == RESULT_OK ){
            Uri uri = data.getData();
        }
    }

}