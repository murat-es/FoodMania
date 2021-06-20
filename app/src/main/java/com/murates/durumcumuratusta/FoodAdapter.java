package com.murates.durumcumuratusta;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    List<FoodItem> data;
    Context context;
    int selectedItem = 0;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    Boolean favoriteChecker = false;
    DatabaseReference  fvrtRef, fvrt_listRef;


    public FoodAdapter(List<FoodItem> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        fvrtRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid()).child("Favorites");
        fvrt_listRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid()).child("FavoritesList");


        View view = layoutInflater.inflate(R.layout.food_holder, parent, false);

        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {

        holder.price.setText(data.get(position).getPrice()+"$");
        //Picasso.get().load(data.get(position).getImage()).into(holder.image);
       // Glide.with(context).load(data.get(position).getImage()).into(holder.image);
        Glide.with(context).load(data.get(position).getImage()).into(holder.image);

        holder.title.setText(data.get(position).getName());

        // holder.ratingBar.setRating((data.get(position).getRating());

        if (selectedItem == position) {
            holder.cardView.animate().scaleX(1.0f);
            holder.cardView.animate().scaleY(1.0f);
            holder.title.setTextColor(Color.BLACK);
            holder.price.setTextColor(Color.BLACK);
            holder.llBackground.setBackgroundResource(R.color.colorWhite);
        } else {
            holder.cardView.animate().scaleX(0.9f);
            holder.cardView.animate().scaleY(0.9f);
            holder.title.setTextColor(Color.GRAY);
            holder.price.setTextColor(Color.GRAY);
            holder.llBackground.setBackgroundResource(R.color.colorWhite);
        }




        String userid = mAuth.getCurrentUser().getUid();
        holder.favoriteChecker(userid);
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String saveCurrentTime;
                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForDate.getTime());

                final HashMap<String, Object> cartMap = new HashMap<>();

                favoriteChecker = true;
                fvrtRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (favoriteChecker.equals(true)) {
                            if (snapshot.hasChild(holder.title.getText().toString())) {
                                fvrtRef.child(holder.title.getText().toString()).removeValue();
                                delete();
                                favoriteChecker = false;
                            } else {
                                fvrtRef.child(holder.title.getText().toString()).setValue(true);
                                cartMap.put("productTitle", holder.title.getText().toString());
                                cartMap.put("productPrice", holder.price.getText().toString().replace("$", ""));
                                cartMap.put("productQuantity", holder.quantity.getText().toString());
                                cartMap.put("productImage", holder.image.toString());
                                cartMap.put("Time", saveCurrentTime);

                                fvrt_listRef.child(holder.title.getText().toString()).setValue(cartMap);
                                favoriteChecker = false;

                            }
                        }

    }
                    private void delete() {

                        FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid()).child("FavoritesList")
                                .child(holder.title.getText().toString())
                                .removeValue();
                        holder.favorite.setImageResource(R.drawable.ic_favorite_red);
                        fvrt_listRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    System.out.println(dataSnapshot);
                                    dataSnapshot.child(holder.title.getText().toString()).getRef().removeValue();
                                    //Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        ImageView image, addButton, removeButton,favorite;
        TextView title, quantity;
        TextView price;
        LinearLayout llBackground;
        CardView cardView;
        Button addToCart;
        int totalQuantity;
        DatabaseReference favouriteref;

        public FoodHolder(View holder) {
            super(holder);
            ratingBar = holder.findViewById(R.id.rating);
            title = holder.findViewById(R.id.food_title);
            image = holder.findViewById(R.id.food_img);
            price = holder.findViewById(R.id.txt_price);
            cardView = holder.findViewById(R.id.food_background);
            llBackground = holder.findViewById(R.id.ll_background);
            addButton = holder.findViewById(R.id.addItem);
            removeButton = holder.findViewById(R.id.removeItem);
            quantity = holder.findViewById(R.id.quantity);
            addToCart = holder.findViewById(R.id.addToCart);
            favorite=itemView.findViewById(R.id.fav_shadow);


            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCartt();
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (totalQuantity < 15) {
                        totalQuantity++;
                        quantity.setText(String.valueOf(totalQuantity));
                    }
                }
            });
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (totalQuantity > 1) {
                        totalQuantity--;
                        quantity.setText(String.valueOf(totalQuantity));
                    }
                }
            });


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });


        }

        public void favoriteChecker(String a) {
            favorite = itemView.findViewById(R.id.fav_shadow);
            favouriteref = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid()).child("Favorites");

            favouriteref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(title.getText().toString())) {
                        favorite.setImageResource(R.drawable.ic_favorite_red);
                      //  Toast.makeText(context, "Favoried", Toast.LENGTH_SHORT).show();
                    } else {
                        favorite.setImageResource(R.drawable.ic_favorite_shadow);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        private void addToCartt() {
            String saveCurrentTime, saveCurrentDate;
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());
            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("productTitle", title.getText().toString());
            cartMap.put("productPrice", price.getText().toString().replace("$", ""));
            cartMap.put("productQuantity", quantity.getText().toString());
            cartMap.put("Time", saveCurrentTime);
            cartMap.put("Date", saveCurrentDate);
            String user = mAuth.getCurrentUser().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("users")
                    .child(mAuth.getCurrentUser().getUid()).child("CartList").child(title.getText().toString());
            databaseReference.setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, title.getText().toString() +" Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}