package com.myfirstapp.mentdoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class AppointmentListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<appointmentDetails> appointmentList;
    ArrayList<docDetails> docList;

    AppointmentAdapter appointmentAdapter;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        ImageView backbtn = findViewById(R.id.appointmentBackBtn);

        mAuth = FirebaseAuth.getInstance();

        docList = (ArrayList<docDetails>) getIntent().getSerializableExtra("docList");

        recyclerView = findViewById(R.id.appointmentList_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appointmentList = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(AppointmentListActivity.this,appointmentList);
        recyclerView.setAdapter(appointmentAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("appointments")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Access document data using document.getData()
                            Map<String, Object> data = document.getData();
                            String date = (String) data.get("date");
                            String doc_id = (String) data.get("doc_id");
                            String slot = (String) data.get("slot");
                            String docName=null;
                            String slot_time=null;
                            String docPost = null;

                            String appointment_id = document.getId();

                            for (docDetails details : docList){
                                if(details.getId().equals(doc_id)){
                                    docName = details.getName();
                                    docPost = details.getPost();
                                    if (slot.equals("slot1")){
                                        slot_time = details.getSlot1();
                                    }else if (slot.equals("slot2")){
                                        slot_time = details.getSlot2();
                                    }else if (slot.equals("slot3")){
                                        slot_time = details.getSlot3();
                                    }else if (slot.equals("slot4")){
                                        slot_time = details.getSlot4();
                                    }else if (slot.equals("slot5")){
                                        slot_time = details.getSlot5();
                                    }else if (slot.equals("slot6")){
                                        slot_time = details.getSlot6();
                                    }
                                }
                            }

                            appointmentDetails appointmentDetail = new appointmentDetails(date,doc_id,slot,docName,slot_time,docPost,appointment_id);
                            appointmentList.add(appointmentDetail);

                        }
                        appointmentAdapter.notifyDataSetChanged();
                    }
                });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}