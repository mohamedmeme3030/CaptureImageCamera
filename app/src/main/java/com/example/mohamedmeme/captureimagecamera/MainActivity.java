package com.example.mohamedmeme.captureimagecamera;

import android.content.Intent;
import android.net.Uri;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

private Button btnUpload;
private ImageView ivUploaded;

private static final int IMAGE_REQUEST_CODE=1;
private StorageReference storageReference;
private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUpload=findViewById(R.id.btn_capture);
        ivUploaded=findViewById(R.id.iv_uploaded);

        FirebaseApp.initializeApp(this);
        storageReference=FirebaseStorage.getInstance().getReference() ;

        progressBar=new ProgressBar(this);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,IMAGE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST_CODE&&resultCode==RESULT_OK){


            progressBar.setVisibility(View.VISIBLE);

            Uri uri=data.getData();
            StorageReference filePath=storageReference.child("Image").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Uploaded Finishing...", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }
}
