package com.murates.durumcumuratusta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartHolder> {
    List<MyCart> myCartList;
    Context context;

    public MyCartAdapter(List<MyCart> myCartList, Context context) {
        this.myCartList = myCartList;
        this.context = context;
    }

    DatabaseReference cartListRef;
    FirebaseAuth mAuth;

    @NonNull
    @Override
    public MyCartAdapter.MyCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mAuth  = FirebaseAuth.getInstance();
        cartListRef = FirebaseDatabase.getInstance().getReference().child("users")
                .child(mAuth.getCurrentUser().getUid()).child("CartList");

        return new MyCartHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.MyCartHolder holder, int position) {
        holder.cPrice.setText("Price : " + myCartList.get(position).getCartPrice()+"$");
        holder.cTitle.setText(myCartList.get(position).getCartName());
        holder.cQuantity.setText("Piece : " + myCartList.get(position).getCartQuantity());



        holder.deleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String productName= myCartList.get(position).getCartName();
                    cartListRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(mAuth.getCurrentUser().getUid()).child("CartList").child(productName).removeValue().
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context,productName+" removed successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCartList.size();
    }

    class MyCartHolder extends RecyclerView.ViewHolder{

        TextView cTitle,cPrice,cQuantity;
        ImageView deleteCart;

        public MyCartHolder(@NonNull View itemView) {
            super(itemView);
            cTitle=itemView.findViewById(R.id.cart_title);
            cPrice=itemView.findViewById(R.id.cart_price);
            cQuantity=itemView.findViewById(R.id.cart_quantity);
            deleteCart=itemView.findViewById(R.id.cancel);
        }
    }
}
