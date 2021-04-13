package com.murates.durumcumuratusta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        NavController navController= Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.Home);
        topLevelDestinations.add(R.id.Search);
        topLevelDestinations.add(R.id.Basket);
        topLevelDestinations.add(R.id.Others);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        mAuth=FirebaseAuth.getInstance();



    }




    @Override
    protected void onStart() {
        super.onStart();
        TextView textView=findViewById(R.id.welcomeUser);
        textView.setText("Welcome "+mAuth.getCurrentUser().getEmail());
        }

        public void logout(View view){
            mAuth.signOut();
            Intent intent=new Intent(this,sign_in.class);
            startActivity(intent);
        }
    }

