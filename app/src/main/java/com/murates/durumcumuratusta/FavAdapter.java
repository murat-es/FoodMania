package com.murates.durumcumuratusta;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public  class FavAdapter extends RecyclerView.Adapter<FavAdapter.MyFavoriteHolder> {

    List<FavRecycle> favList;
    Context context;


    DatabaseReference favListRef;


    private StorageReference mStorageRef;

    public FavAdapter(List<FavRecycle> favList, Context context) {
        this.favList = favList;
        this.context = context;
    }

    DatabaseReference favRef;
    @NonNull
    @Override
    public FavAdapter.MyFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        favRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser()
            .getUid()).child("Favorites");
        favListRef = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser()
                .getUid()).child("FavoritesList");

        return new FavAdapter.MyFavoriteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_fav_recycle, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.MyFavoriteHolder holder, int position) {


        String name= favList.get(position).getName().toLowerCase();


        mStorageRef = FirebaseStorage.getInstance().getReference().child("photos")
        .child(name+".jpg");

        mStorageRef.getBytes(100*100)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        holder.favImage.setImageBitmap(bitmap);
                    }
                });


        holder.favName.setText(favList.get(position).getName());
        holder.favPrice.setText("Price: "+favList.get(position).getPrice()+"$");
        //Picasso.get().load(favList.get(position).getImage()).into(holder.favImage);
    //    Glide.with(context).load(favList.get(position).getImage()).into(holder.favImage);



        holder.deleteFromFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName= favList.get(position).getName();

               favRef.child(productName).removeValue();
                favListRef.child(productName).removeValue();

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    class MyFavoriteHolder extends RecyclerView.ViewHolder{

        ImageView deleteFromFav;
        TextView favName, favPrice, favQuantity;
        ImageView favImage;

        public MyFavoriteHolder(@NonNull View itemView) {
            super(itemView);

            favName=itemView.findViewById(R.id.cartNm);
            favPrice=itemView.findViewById(R.id.cartPrc);
            favImage=itemView.findViewById(R.id.cartImg);
           deleteFromFav = itemView.findViewById(R.id.cancelFav);
        }
    }
}