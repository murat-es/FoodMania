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

public class MyFav extends AppCompatActivity {
    FirebaseAuth mAuth;
    List<FavRecycle> favoritesList;
    FavAdapter favoriteAdapter;
    RecyclerView favRecycler;
    DatabaseReference databaseReference;
    ImageView goBack;
    ImageView deleteFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> finish());

        deleteFav=findViewById(R.id.cancelFav);

        favRecycler = findViewById(R.id.recyclerViewFav);
        favRecycler.setHasFixedSize(true);
        favoritesList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(MyFav.this, RecyclerView.VERTICAL, false);
        favRecycler.setLayoutManager(manager);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoritesList.clear();

                for (DataSnapshot ds : snapshot.child("users").child(mAuth.getCurrentUser().getUid()).child("FavoritesList").getChildren()) {
                    HashMap<String, String> map = new HashMap<>();
                    for (DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(), (String) child.getValue());
                    }
                    String favTitle =  map.get("productTitle");
                    String favImage =  map.get("productImage");
                    String favPrice =  map.get("productPrice");
                    favoritesList.add(new FavRecycle(favTitle, favPrice, favImage));
                }
                favoriteAdapter = new FavAdapter(favoritesList,MyFav.this);
                favoriteAdapter.notifyDataSetChanged();
                favRecycler.setAdapter(favoriteAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}