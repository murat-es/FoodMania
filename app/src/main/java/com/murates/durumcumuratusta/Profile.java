package com.murates.durumcumuratusta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class Profile extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    EditText pName,pEmail,pPass;
    Button update;
    String oldEmail,oldPassword;
    FirebaseUser user;
    ImageView pp;
    Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReferenceDown,storageReferenceUp;
    ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pName=findViewById(R.id.profileName);
        pEmail=findViewById(R.id.profileEmail);
        pPass=findViewById(R.id.profilePassword);
        pp=findViewById(R.id.profileImage);

        oldEmail=pEmail.getText().toString();
        oldPassword=pPass.getText().toString();

        goBack = findViewById(R.id.goBackToSet);
        goBack.setOnClickListener(v -> finish());

        storage=FirebaseStorage.getInstance();
        storageReferenceDown=storage.getReference();
        storageReferenceUp=storage.getReference();


        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference()
        .child("users").child(mAuth.getCurrentUser().getUid()).child("userInfo");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                    String email = snapshot.child("email").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();
                    String password = snapshot.child("password").getValue().toString();

                    pName.setText(name);
                    pEmail.setText(email);
                    pPass.setText(password);


                storageReferenceUp=FirebaseStorage.getInstance().getReference().child("userProfile").child(user.getUid());

                try {
                    File localfile=File.createTempFile(user.getUid(),"jpg");
                    storageReferenceUp.getFile(localfile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                  //  Toast.makeText(Profile.this,"Image retrieved",Toast.LENGTH_SHORT).show();
                                    Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                    pp.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Profile.this,"Failed ",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update=findViewById(R.id.updateProfile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("name").setValue(pName.getText().toString());
                databaseReference.child("email").setValue(pEmail.getText().toString());
                databaseReference.child("password").setValue(pPass.getText().toString());


                user.updateEmail(pEmail.getText().toString());
                user.updatePassword(pPass.getText().toString());
            }
        });

        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
    }

    private void choosePicture() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            pp.setImageURI(imageUri);
            uploadPicture();
        }
    }

        private void uploadPicture() {

            final String userID=user.getUid();
            StorageReference riversRef = storageReferenceDown.child("userProfile/"+userID);

            riversRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Snackbar.make(findViewById(android.R.id.content),"Image uploaded",Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(),"Failed to upload",Toast.LENGTH_LONG).show();
                        }
                    });
    }
}