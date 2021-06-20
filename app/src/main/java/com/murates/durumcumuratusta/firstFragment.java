package com.murates.durumcumuratusta;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class firstFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private ArrayList<MainModel> mainModels;
    private ArrayList<MainModel> mainModels2;
    private MainAdapter mainAdapter;
    private MainAdapter mainAdapter2;
    private FirebaseFirestore mFireStore;
    private TextView welcomeUser;
    private FirebaseAuth mAuth;
    private EditText date,time,people;
    private Button makeReservation;
    private DatabaseReference reservationRef;

    public firstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_first, container, false);


        recyclerView=rootView.findViewById(R.id.recycleViewF);
        mFireStore=FirebaseFirestore.getInstance();

      mAuth = FirebaseAuth.getInstance();
       /*   welcomeUser=recyclerView.findViewById(R.id.welcomeUser);
        welcomeUser.setText("Welcome"+ FirebaseDatabase.getInstance().getReference("users").
                child(mAuth.getCurrentUser().getUid()).child("userInfo").child("name").toString());*/

        date=rootView.findViewById(R.id.date_input);
        time=rootView.findViewById(R.id.time_input);
        people=rootView.findViewById(R.id.table_input);
        makeReservation=rootView.findViewById(R.id.makeReser);



        date.setInputType(InputType.TYPE_NULL);
        time.setInputType(InputType.TYPE_NULL);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                 //   InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                   // imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
                showDateDialog(date);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(time);
            }
        });





        makeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date.getText().toString().equals("") || time.getText().toString().equals("") ||
                        people.getText().toString().equals("")){
                    Toast.makeText(getContext(),"entries cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(people.getText().toString())>8){
                    Toast.makeText(getContext(),"The number of people cannot be more than 8",Toast.LENGTH_SHORT).show();
                }
                else {
                    reservationRef = FirebaseDatabase.getInstance().getReference("users")
                            .child(mAuth.getCurrentUser().getUid()).child("Reservation").child(date.getText().toString());

                    reservationRef.child("Date").setValue(date.getText().toString());
                    reservationRef.child("Time").setValue(time.getText().toString());
                    reservationRef.child("People").setValue(people.getText().toString());

                    Toast.makeText(getContext(), "Your reservation has been made", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        LinearLayoutManager managerSales = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(managerSales);
        recyclerView.setAdapter(mainAdapter);




   //2.

        recyclerView2=rootView.findViewById(R.id.recycleView2);
        mainModels2=new ArrayList<>();
        recyclerView2=recyclerView2.findViewById(R.id.recycleView2);
        mainAdapter2=new MainAdapter(getContext(),mainModels2);
        recyclerView2.setHasFixedSize(true);

        CollectionReference collectionReference2 = mFireStore.collection("campaigns");
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

        return rootView;
    }

    private void showDateDialog(final EditText date_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog(final EditText time_in) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("HH:mm"));
                time_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE), false).show();
    }
}