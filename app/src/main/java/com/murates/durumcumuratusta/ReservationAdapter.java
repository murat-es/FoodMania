package com.murates.durumcumuratusta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationHolder>{

    private ArrayList<ReservationReser> reservationListt;
    private Context context;

    public ReservationAdapter(ArrayList<ReservationReser> reservationRecyclers, Context context) {
        this.reservationListt = reservationRecyclers;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservationAdapter.ReservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_reservation_reser, parent, false);
        return new ReservationAdapter.ReservationHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ReservationHolder holder, int position) {

        if (reservationListt.size()>0){
            holder.resDate.setText(reservationListt.get(position).getDate());
            holder.resTime.setText(reservationListt.get(position).getTime());
            holder.resNumPeople.setText(reservationListt.get(position).getNumOfPerson());

        }

    }

    @Override
    public int getItemCount() {
        return reservationListt.size();
    }

    class ReservationHolder extends RecyclerView.ViewHolder{

        TextView resDate,resTime,resNumPeople;

        public ReservationHolder(@NonNull View itemView) {
            super(itemView);

            resDate = itemView.findViewById(R.id.resDate);
            resTime = itemView.findViewById(R.id.resTime);
            resNumPeople = itemView.findViewById(R.id.resPeople);
        }
    }
}

