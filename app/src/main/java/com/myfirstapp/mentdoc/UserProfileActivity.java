package com.myfirstapp.mentdoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserProfileActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    String userId;
    FirebaseAuth mAuth;
    TextView ageView,nameView,emailView,mblNumView,guardianNameView,guardianNumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ageView = findViewById(R.id.tv_age);
        nameView = findViewById(R.id.tv_name);
        emailView = findViewById(R.id.tv_email);
        mblNumView = findViewById(R.id.tv_num);
        guardianNameView = findViewById(R.id.tv_guardian_name);
        guardianNumView = findViewById(R.id.tv_guardian_num);


        mAuth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();

        userId = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                nameView.setText(documentSnapshot.getString("full_name"));
                ageView.setText(documentSnapshot.getString("age"));
                emailView.setText(documentSnapshot.getString("email"));
                mblNumView.setText(documentSnapshot.getString("mbl_num"));
                guardianNumView.setText(documentSnapshot.getString("guardian_num"));
                guardianNameView.setText(documentSnapshot.getString("guardian_name"));
            }
        });

    }

    public void goToEditProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
        startActivity(intent);
        finish();
    }
}