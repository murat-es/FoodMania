package com.murates.durumcumuratusta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderHolder> {
    List<MyOrder> orderList;
    Context context;

    public MyOrderAdapter(List<MyOrder> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyOrderAdapter.MyOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyOrderAdapter.MyOrderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.MyOrderHolder holder, int position) {
            holder.orderPrice.setText("Order price:  "+orderList.get(position).getOrderPrice()+"$");
            holder.orderDate.setText("Order date:  "+orderList.get(position).getDate());
            holder.orderTime.setText("Order time:  "+orderList.get(position).getTime());




    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyOrderHolder extends RecyclerView.ViewHolder {
        TextView orderPrice,orderDate,orderTime;
        Button order;
        public MyOrderHolder(@NonNull View itemView) {
            super(itemView);
            orderPrice=itemView.findViewById(R.id.orderPrice);
            orderDate=itemView.findViewById(R.id.orderDate);
            orderTime=itemView.findViewById(R.id.orderTime);
            order=itemView.findViewById(R.id.btn_order);


        }

    }
}
