package com.myfirstapp.mentdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DocAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<docDetails> docList;
    DocAdapter docAdapter;

    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_all);

        recyclerView = findViewById(R.id.docList_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        docList = new ArrayList<>();
        docAdapter = new DocAdapter(DocAllActivity.this, docList);
        recyclerView.setAdapter(docAdapter);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mentdoc-da69c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            // Inside onDataChange method
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                docList.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("DocDetails").getChildren()) {

                    String getId = dataSnapshot.getKey();
                    String getName = dataSnapshot.child("name").getValue(String.class);
                    String getPost = dataSnapshot.child("post").getValue(String.class);
                    String getTiming = dataSnapshot.child("timing").getValue(String.class);
                    String getImage = dataSnapshot.child("image").getValue(String.class);
                    String getSlot1 = dataSnapshot.child("slot1").getValue(String.class);
                    String getSlot2 = dataSnapshot.child("slot2").getValue(String.class);
                    String getSlot3 = dataSnapshot.child("slot3").getValue(String.class);
                    String getSlot4 = dataSnapshot.child("slot4").getValue(String.class);
                    String getSlot5 = dataSnapshot.child("slot5").getValue(String.class);
                    String getSlot6 = dataSnapshot.child("slot6").getValue(String.class);


                    docDetails details = new docDetails(getId,getName, getPost, getTiming, getImage,getSlot1,getSlot2,getSlot3,getSlot4,getSlot5,getSlot6);
                    Log.d("docname", "onDataChange: Added"+getName);
                    docList.add(details);


                }
//                Collections.reverse(cowList);
                docAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    public void BacktoHome(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}