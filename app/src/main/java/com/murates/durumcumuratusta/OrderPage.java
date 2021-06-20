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

public class OrderPage extends AppCompatActivity {
    FirebaseAuth mAuth;
    List<MyOrder> orderList;
    MyOrderAdapter orderAdapater;
    RecyclerView orderRecycle;
    DatabaseReference databaseReference;
    ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String user = mAuth.getUid();
        goBack = findViewById(R.id.goBack2);
        goBack.setOnClickListener(v -> finish());

        orderRecycle = findViewById(R.id.recyclerViewOrder);
        orderRecycle.setHasFixedSize(true);
        orderList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(OrderPage.this, RecyclerView.VERTICAL, false);
        orderRecycle.setLayoutManager(manager);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();

                for (DataSnapshot ds : snapshot.child("users")
                        .child(mAuth.getCurrentUser().getUid()).child("Orders").getChildren()) {
                    HashMap<String, String> map = new HashMap<>();
                    for (DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(), (String) child.getValue());
                    }
                    String orderPrice = map.get("productPrice");
                    String orderDate = map.get("Date");
                    String orderTime = map.get("Time");
                    orderList.add(new MyOrder(orderPrice, orderDate, orderTime));
                }
                orderAdapater = new MyOrderAdapter(orderList,OrderPage.this);
                orderAdapater.notifyDataSetChanged();
                orderRecycle.setAdapter(orderAdapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}