package com.murates.durumcumuratusta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<MainModel> mainModels;
    Context context;

    public MainAdapter(Context context,ArrayList<MainModel> mainModels){
        this.context=context;
        this.mainModels=mainModels;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     //   holder.imageView.setImageResource(mainModels.get(position).getFoodsPhoto());
     //   holder.textView.setText(mainModels.get(position).foodsName);
        if (mainModels.size() > 0) {
            Picasso.get().load(mainModels.get(position).getImageLink()).into(holder.imageView);
            holder.textFood.setText(mainModels.get(position).getFoodsName());
            holder.textPrice.setText(mainModels.get(position).getFoodsPrice());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,mainModels.get(position).getFoodsName(),Toast.LENGTH_SHORT).show();
                }
            });
          //  holder.rating.setText(mainModels.get(position).getFoodRating());
        }
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textFood;
        TextView textPrice;
        TextView rating;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);
            textFood=itemView.findViewById(R.id.text_view);
            textPrice=itemView.findViewById(R.id.price);
            //rating=itemView.findViewById(R.id.ratingFood);
            layout=itemView.findViewById(R.id.foodLay);
        }
    }
}
