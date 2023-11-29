package com.myfirstapp.mentdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btn;

    FirebaseAuth mAuth;

    LinearLayout btnLogout,btnChildTrack,btnTeenTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnLogout =findViewById(R.id.logout_id);
        btnChildTrack = findViewById(R.id.child_track_id);
        btnTeenTrack = findViewById(R.id.teenage_track_id);

        btnChildTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QuesAnsActivity.class);
                intent.putExtra("category","QuesChild");
                startActivity(intent);
                finish();
            }
        });

        btnTeenTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QuesAnsActivity.class);
                intent.putExtra("category","QuesUnder18");
                startActivity(intent);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();

            }
        });

    }
}