package com.murates.durumcumuratusta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyReservation extends AppCompatActivity {
    ImageView goBack;
    FirebaseAuth mAuth;
    ArrayList<ReservationReser> reservationList;
    ReservationAdapter reservationAdapterAdapater;
    RecyclerView reservationRecycle;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        goBack = findViewById(R.id.gotoMenu);
        goBack.setOnClickListener(v -> finish());

        reservationRecycle = findViewById(R.id.recyclerViewRes);
        reservationRecycle.setHasFixedSize(true);
        reservationList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(MyReservation.this, RecyclerView.VERTICAL, false);
        reservationRecycle.setLayoutManager(manager);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservationList.clear();

                for (DataSnapshot ds : snapshot.child("users")
                        .child(mAuth.getCurrentUser().getUid()).child("Reservation").getChildren()) {
                    HashMap<String, String> map = new HashMap<>();
                    for (DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(), (String) child.getValue());
                    }
                    String resDate = map.get("Date");
                    String resPeople = map.get("People");
                    String resTime = map.get("Time");
                    reservationList.add(new ReservationReser("Reservation date: " + resDate ,
                            "Reservation Time: " + resTime ,
                            "Number of the people: " +resPeople));
                }
                reservationAdapterAdapater = new ReservationAdapter(reservationList,MyReservation.this);
                reservationAdapterAdapater.notifyDataSetChanged();
                reservationRecycle.setAdapter(reservationAdapterAdapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
