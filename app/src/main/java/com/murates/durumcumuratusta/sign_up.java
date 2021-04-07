package com.murates.durumcumuratusta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_up extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth=FirebaseAuth.getInstance();
    }
    public void signup(View view){
        EditText editTextName=findViewById(R.id.editTextName);
        String name=editTextName.getText().toString();

        EditText editTextEmail=findViewById(R.id.editTextEmail);
        String email=editTextEmail.getText().toString();

        EditText editTextPassword=findViewById(R.id.editTextPassword);
        String password=editTextPassword.getText().toString();

        EditText editTextPasswordAgain=findViewById(R.id.editTextPasswordAgain);
        String passwordAgain=editTextPassword.getText().toString();

        if(!password.equals(passwordAgain)){
            Toast.makeText(sign_up.this,"Password confirmation does not match!",Toast.LENGTH_SHORT).show();
        }
        else {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user=auth.getCurrentUser();
                        System.out.println(user.getEmail());
                    }
                    else {
                        Toast.makeText(sign_up.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
