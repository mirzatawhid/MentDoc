package com.myfirstapp.mentdoc;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Button btn;

    FirebaseAuth mAuth;

    RecyclerView recyclerView;

    ArrayList<docDetails> docList;
    DocAdapter docAdapter;

    ValueEventListener eventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_view);
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


        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mentdoc-da69c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            // Inside onDataChange method
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                docList.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("DocDetails").getChildren()) {

                    String getName = dataSnapshot.child("name").getValue(String.class);
                    String getPost = dataSnapshot.child("post").getValue(String.class);
                    String getTiming = dataSnapshot.child("timing").getValue(String.class);
                    String getImage = dataSnapshot.child("image").getValue(String.class);


                    docDetails details = new docDetails(getName, getPost, getTiming, getImage);
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
}