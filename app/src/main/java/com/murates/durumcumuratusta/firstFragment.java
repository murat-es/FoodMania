package com.murates.durumcumuratusta;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link firstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class firstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    ArrayList<MainModel> mainModels;
    ArrayList<MainModel> mainModels2;
    MainAdapter mainAdapter;
    MainAdapter mainAdapter2;
    FirebaseFirestore mFireStore;


    LinearLayout linearLayout;


    public firstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment firstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static firstFragment newInstance(String param1, String param2) {
        firstFragment fragment = new firstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_first, container, false);
        recyclerView=rootView.findViewById(R.id.recycleViewF);
        mFireStore=FirebaseFirestore.getInstance();




        /*Integer[] foodsPhoto={R.drawable.pizza,R.drawable.anteplahmacun,R.drawable.atolyeburger,R.drawable.beytikebab,R.drawable.beytikebab};
        String [] foodsName={"pizza","lahmacun","hamburger","iskender","doner"};

        mainModels=new ArrayList<>();
        for (int i = 0; i <foodsPhoto.length ; i++) {

            MainModel model=new MainModel(foodsPhoto[i],foodsName[i]);
            mainModels.add(model);
        }*/

        //----------------------------------------------------------------------
        mainModels=new ArrayList<>();
        recyclerView=recyclerView.findViewById(R.id.recycleViewF);
        mainAdapter=new MainAdapter(getContext(),mainModels);
        recyclerView.setHasFixedSize(true);

        CollectionReference collectionReference = mFireStore.collection("popularFoods");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)
                    Toast.makeText(getActivity(),error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                else if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> map = snapshot.getData();
                        String URL = (String) map.get("ImageLink");
                        String title = (String) map.get("ImageTitle");
                        String rating = (String) map.get("ImageRating");
                        String price = (String) map.get("ImagePrice");

                        mainModels.add(new MainModel(URL,title,price,rating));
                        mainAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        /*RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) layoutManager).setOrientation(recyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);*/
        LinearLayoutManager managerSales = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(managerSales);
        recyclerView.setAdapter(mainAdapter);


       // recyclerView.setAdapter(mainAdapter);

   //2.
        /*
        recyclerView2=rootView.findViewById(R.id.recycleView2);
        mainModels2=new ArrayList<>();
        recyclerView2=recyclerView.findViewById(R.id.recycleView2);
        mainAdapter2=new MainAdapter(getContext(),mainModels2);
        recyclerView2.setHasFixedSize(true);

        CollectionReference collectionReference2 = mFireStore.collection("popularFoods");
        collectionReference2.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)
                    Toast.makeText(getActivity(),error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                else if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> map = snapshot.getData();
                        String URL = (String) map.get("ImageLink");
                        String title = (String) map.get("ImageTitle");
                        String rating = (String) map.get("ImageRating");
                        String price = (String) map.get("ImagePrice");

                        mainModels2.add(new MainModel(URL,title,price,rating));
                        mainAdapter2.notifyDataSetChanged();
                    }
                }
            }
        });
        LinearLayoutManager managerSales2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(managerSales2);
        recyclerView2.setAdapter(mainAdapter2);
*/




        recyclerView2=rootView.findViewById(R.id.recycleView2);

        Integer[] foods2Photo={R.drawable.pizza,R.drawable.anteplahmacun,R.drawable.atolyeburger,R.drawable.beytikebab,R.drawable.beytikebab};
        String [] foods2Name={"pizza","lahmacun","hamburger","iskender","doner"};

        mainModels2=new ArrayList<>();
        for (int i = 0; i <foods2Photo.length ; i++) {
            MainModel model2=new MainModel(foods2Photo[i],foods2Name[i]);

            mainModels2.add(model2);
        }

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) layoutManager2).setOrientation(recyclerView2.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);


        mainAdapter2=new MainAdapter(null,mainModels2);
        recyclerView2.setAdapter(mainAdapter2);


        return rootView;
    }




}
