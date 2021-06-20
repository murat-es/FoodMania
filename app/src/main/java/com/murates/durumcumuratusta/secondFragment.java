package com.murates.durumcumuratusta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class secondFragment extends Fragment {
    private FirebaseFirestore firestore;
    private RecyclerView recyclerCategories;
    private RecyclerView recyclerItems;
    private FoodAdapter fAdapter;
    private ArrayList<FoodCategory> data;
    private DatabaseReference categoryRef,menuRef,mUserDatabase,searchRef;
    private ListView listData;
    private AutoCompleteTextView autoText;


    public secondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_second, container, false);
        firestore = FirebaseFirestore.getInstance();

        searchRef=FirebaseDatabase.getInstance().getReference("desserts");
        listData=view.findViewById(R.id.listSearch);

        autoText=view.findViewById(R.id.searc_bar);


        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        searchRef.addListenerForSingleValueEvent(eventListener);


        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        data = new ArrayList<>();

        recyclerCategories = view.findViewById(R.id.recycler_categories);
        recyclerItems = view.findViewById(R.id.recycler_food);


        FoodCategoryAdapter cAdapter = new FoodCategoryAdapter(data, getContext(), new FoodCategoryAdapter.OnCategoryClick() {
            @Override
            public void onClick(int pos) {
                setFoodItem(pos);
            }
        });
        recyclerCategories.setHasFixedSize(true);

        categoryRef= FirebaseDatabase.getInstance().getReference();
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<String,String > map=new HashMap<>();
                for(DataSnapshot ds : snapshot.child("categoriesName").getChildren()) {
                    for (DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(),(String)child.getValue());
                    }
                    data.add(new FoodCategory((String) map.get("categoriesTitle"),(String) map.get("ImageLink")));
                    cAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

            LinearLayoutManager managerCategories = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerCategories.setLayoutManager(managerCategories);
        recyclerCategories.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();


        setFoodItem(0);

        return view;
    }

    private void populateSearch(DataSnapshot snapshot) {
        ArrayList<String> names=new ArrayList<>();
        if (snapshot.exists()){
            for (DataSnapshot ds:snapshot.getChildren()){
                String name=ds.child("Imagetitle").getValue(String.class);
                names.add(name);
            }
            ArrayAdapter adapter=new ArrayAdapter(getContext()  ,android.R.layout.simple_list_item_1,names);
            autoText.setAdapter(adapter);
            autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name=autoText.getText().toString();
                    searchFood(name);
                }
            });
        }else {
            Log.d("food","no data found");
        }
    }

    private void searchFood(String name) {
        Query query=searchRef.orderByChild("Imagetitle").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()){

                    ArrayList<String> listFood=new ArrayList<>();
                    for (DataSnapshot ds:snapshot.getChildren()){
                        Food food=new Food(ds.child("Imagetitle").getValue().toString(),ds.child("ImagePrice").getValue().toString());
                        listFood.add(food.getName()+"\n"+food.getPrice());
                    }
                    ArrayAdapter adapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,listFood);
                    listData.setAdapter(adapter);

                }else {
                    Log.d("food","no data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    class Food{
        public String name;
        public String price;

        public Food(String name, String price) {
            this.name = name;
            this.price = price;
        }

        public Food() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    private void setFoodItem(int pos) {
        ArrayList<FoodItem> foodItem = new ArrayList<>();

        switch (pos) {
            case 5:
                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("chicken").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new FoodItem(map.get("Imagetitle"), map.get("ImagePrice"), map.get("ImageLink")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case 4:

                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("pizza").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new FoodItem(map.get("Imagetitle"), map.get("ImagePrice"), map.get("ImageLink")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case 3:

                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("hamburger").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new FoodItem(map.get("Imagetitle"), map.get("ImagePrice"), map.get("ImageLink")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:

                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("soup").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new FoodItem(map.get("Imagetitle"), map.get("ImagePrice"), map.get("ImageLink")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 1:

                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("drinks").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new FoodItem(map.get("Imagetitle"), map.get("ImagePrice"), map.get("ImageLink")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 0:


                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("desserts").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new FoodItem(map.get("Imagetitle"), map.get("ImagePrice"), map.get("ImageLink")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

        fAdapter = new FoodAdapter(foodItem,getContext());
        LinearLayoutManager managerItems = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerItems.setLayoutManager(managerItems);
        recyclerItems.setAdapter(fAdapter);
    }
}