package com.myfirstapp.mentdoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    TextView navName;

    Button btn;

    FirebaseAuth mAuth;

    RecyclerView recyclerView;

    ArrayList<docDetails> docList;
    DocAdapter docAdapter;

    ValueEventListener eventListener;

    FirebaseFirestore firestore;
    String userId;
    int age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.inflateHeaderView(R.layout.header);
        navName = hView.findViewById(R.id.nav_name);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);



        //Recylerview Doctor List

        recyclerView = findViewById(R.id.home_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        docList = new ArrayList<>();
        docAdapter = new DocAdapter(MainActivity.this, docList);
        recyclerView.setAdapter(docAdapter);

        //for tracking
        userId = mAuth.getCurrentUser().getUid();
        Log.d("userid", "onCreate: uid = "+userId);

        firestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = firestore.collection("users").document(userId);

        Log.d("checkingflow", "onCreate: before");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                navName.setText(documentSnapshot.getString("full_name"));
                age = Integer.parseInt(documentSnapshot.getString("age"));

            }
        });


        //for top doctor list
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
                    String rating = dataSnapshot.child("rating").getValue(String.class);


                    docDetails details = new docDetails(getId,getName, getPost, getTiming, getImage,getSlot1,getSlot2,getSlot3,getSlot4,getSlot5,getSlot6,rating);
                    Log.d("docname", "onDataChange: Added"+getName);
                    docList.add(details);


                }

                docAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


//        btnChildTrack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),QuesAnsActivity.class);
//                intent.putExtra("category","QuesChild");
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        btnTeenTrack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),QuesAnsActivity.class);
//                intent.putExtra("category","QuesUnder18");
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        btnAdultTrack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),QuesAnsActivity.class);
//                intent.putExtra("category","QuesAdult");
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_share){
            Toast.makeText(this, "Share button is clicked!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        }

        return true;
    }

    public void FullDocList(View view) {
        Intent intent = new Intent(getApplicationContext(),DocAllActivity.class);
        startActivity(intent);
    }

    public void GoToTracking(View view) {
        String category;
        if(age <13){
            category = "QuesChild";
        } else if (age>12 && age<20) {
            category="QuesUnder18";
        }else{
            category="QuesAdult";
        }
        Intent intent = new Intent(getApplicationContext(),QuesAnsActivity.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }

    public void goToAdvice(View view) {
        Intent intent = new Intent(getApplicationContext(),AdviceActivity.class);
        startActivity(intent);
    }

    public void goToAppointmentList(View view) {
        Intent intent = new Intent(getApplicationContext(),AppointmentListActivity.class);
        intent.putExtra("docList",docList);
        startActivity(intent);
    }
}