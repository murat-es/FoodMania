package com.murates.durumcumuratusta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class forgetPassword extends AppCompatActivity {
    EditText editText;
    Button button;
    ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        editText=findViewById(R.id.editTextMail);
        button=findViewById(R.id.button);
        progressBar=findViewById(R.id.progressBar);

        auth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        String email=editText.getText().toString().trim();

        if (email.isEmpty()){
            editText.setError("Email is required!");
            editText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText.setError("Please enter a valid email!");
            editText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(forgetPassword.this,"Check your email to reset password!",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(forgetPassword.this,"Try again! Something wrong happened!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void goToSignup(View view){
        Intent intent=new Intent(this,sign_up.class);
        startActivity(intent);
        finish();
    }
    public void goSignIn(View view){
        Intent intent=new Intent(this,sign_in.class);
        startActivity(intent);
    }
}
