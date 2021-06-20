package com.murates.durumcumuratusta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class ThirdFragment extends Fragment {

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    TextView sumTotal;
    List<MyCart> myCartList;
    MyCartAdapter myCartAdapter;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third, container, false);

        mAuth = FirebaseAuth.getInstance();

        sumTotal =view.findViewById(R.id.total_price);

        RecyclerView cartRecycler = view.findViewById(R.id.recycler_cart);
        cartRecycler.setHasFixedSize(true);
        myCartList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        cartRecycler.setLayoutManager(manager);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        view.findViewById(R.id.btn_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"Your order has been received.",Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference("users")
                        .child(mAuth.getCurrentUser().getUid()).child("CartList").removeValue();
                            }
        });



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myCartList.clear();

                for (DataSnapshot ds : snapshot.child("users")
                        .child(mAuth.getCurrentUser().getUid()).child("CartList").getChildren()) {
                    HashMap<String, String> map = new HashMap<>();
                    for (DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(), (String) child.getValue());
                    }
                    String cartTitle = map.get("productTitle");
                    String cartQuantity = map.get("productQuantity");
                    String cartPrice = map.get("productPrice");
                    myCartList.add(new MyCart(cartTitle, cartPrice, cartQuantity));
                }
                double sum = 0;
                for (MyCart cart : myCartList) {
                    sum += Double.parseDouble(cart.getCartPrice())*Integer.parseInt(cart.getCartQuantity());
                }
                sumTotal.setText(sum + "$");
                myCartAdapter = new MyCartAdapter(myCartList,getContext());
                myCartAdapter.notifyDataSetChanged();
                cartRecycler.setAdapter(myCartAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Button order=view.findViewById(R.id.btn_order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeOrders();
            }
        });



        return view;
    }
    private void completeOrders() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productPrice", sumTotal.getText().toString().replace("$", ""));
        cartMap.put("Time", saveCurrentTime);
        cartMap.put("Date", saveCurrentDate);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(mAuth.getCurrentUser().getUid()).child("Orders").child(saveCurrentTime);
        databaseReference.setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseDatabase.getInstance().getReference("users")
                        .child(mAuth.getCurrentUser().getUid()).child("CartList").removeValue();
                Toast.makeText(getContext(),  " Order is successful", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
