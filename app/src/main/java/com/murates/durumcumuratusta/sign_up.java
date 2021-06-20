package com.murates.durumcumuratusta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.murates.durumcumuratusta.dto.User;

import java.util.HashMap;

public class sign_up extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private DatabaseReference mReference;
    private HashMap<String,Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth=FirebaseAuth.getInstance();


    }
    public void goSignIn(View view){
        Intent intent=new Intent(this,sign_in.class);
        startActivity(intent);
    }

    public void signup(View view){
        EditText editTextName=findViewById(R.id.editTextName);
        String name=editTextName.getText().toString();

        EditText editTextEmail=findViewById(R.id.editTextEmail);
        String email=editTextEmail.getText().toString();

        EditText editTextPassword=findViewById(R.id.editTextPassword);
        String password=editTextPassword.getText().toString();

        EditText editTextPasswordAgain=findViewById(R.id.editTextPasswordAgain);
        String passwordAgain=editTextPasswordAgain.getText().toString();


        User userInfo=new User(name,email,password);

        if(!password.equals(passwordAgain)){
            Toast.makeText(sign_up.this,"Password confirmation does not match!",Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.matches("")) {
            Toast.makeText(this, "You did not enter a name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.matches("")) {
            Toast.makeText(this, "You did not enter a e-mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.matches("")) {
            Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordAgain.matches("")) {
            Toast.makeText(this, "You did not enter password confirm", Toast.LENGTH_SHORT).show();
            return;
        }


        else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(sign_up.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user=mAuth.getCurrentUser();
                        mReference= FirebaseDatabase.getInstance().getReference();
                        mReference.child("users").child(user.getUid()).child("userInfo").setValue(userInfo);

                      /*  Toast.makeText(sign_up.this,
                                "User created", Toast.LENGTH_SHORT).show();*/

                      /*
                        mData=new HashMap<>();
                        mData.put("userName",name);
                        mData.put("userEMail",email);
                        mData.put("userPassword",password);
                        mData.put("userID",user.getUid());



                        mReference.child("users").child(user.getUid()).setValue(mData)
                                .addOnCompleteListener(sign_up.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(sign_up.this,"succesfull",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(sign_up.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });*/

                        startActivity(new Intent(sign_up.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(sign_up.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
