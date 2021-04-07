package com.murates.durumcumuratusta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();
        TextView textView=findViewById(R.id.textNameWelcome);
        textView.setText("Welcome "+auth.getCurrentUser().getEmail());
        }
    }

