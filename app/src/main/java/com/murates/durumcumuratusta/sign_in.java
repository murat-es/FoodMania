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

public class sign_in extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth=FirebaseAuth.getInstance();
    }
    @Override
    protected void onStart(){
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
            Intent mainIntent=new Intent(this,MainActivity.class);
            startActivity(mainIntent);
        }
    }


    public void goToSignup(View view){
        Intent intent=new Intent(this,sign_up.class);
        startActivity(intent);
    }

    public void signIn(View view){
        EditText editTextEmail=findViewById(R.id.editTextMail);
        String email=editTextEmail.getText().toString();

        EditText editTextPassword=findViewById(R.id.editTextPassw);
        String password=editTextPassword.getText().toString();

        final Intent mainIntent=new Intent(this,MainActivity.class);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(mainIntent);
                }
                else {
                    Toast.makeText(sign_in.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
