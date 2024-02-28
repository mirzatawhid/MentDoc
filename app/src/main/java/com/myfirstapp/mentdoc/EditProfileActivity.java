package com.myfirstapp.mentdoc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    String userID;

    EditText editName,editAge,editNum,editGuardianName,editGuardianNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth= FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        editName = findViewById(R.id.edit_name);
        editAge = findViewById(R.id.edit_age);
        editNum = findViewById(R.id.edit_mbl);
        editGuardianName = findViewById(R.id.edit_guardian);
        editGuardianNum = findViewById(R.id.edit_guardian_num);
        TextView editMail =findViewById(R.id.edit_email);
        ImageView btnOK = findViewById(R.id.btn_ok);

        mAuth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                editName.setText(documentSnapshot.getString("full_name"));
                editAge.setText(documentSnapshot.getString("age"));
                editMail.setText(documentSnapshot.getString("email"));
                editNum.setText(documentSnapshot.getString("mbl_num"));
                editGuardianName.setText(documentSnapshot.getString("guardian_name"));
                editGuardianNum.setText(documentSnapshot.getString("guardian_num"));
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dName = String.valueOf(editName.getText());
                String dAge = String.valueOf(editAge.getText());
                String dNum = String.valueOf(editNum.getText());
                String dGuardianName = String.valueOf(editGuardianName.getText());
                String dGuardianNum = String.valueOf(editGuardianNum.getText());


                if (TextUtils.isEmpty(dName)) {
                    Toast.makeText(getApplicationContext(), "Enter Full Name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(dAge)) {
                    Toast.makeText(getApplicationContext(), "Enter the Appropiate Age.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(dNum) && dNum.length()>11 ) {
                    Toast.makeText(getApplicationContext(), "Enter the Appropiate Mobile Number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(dGuardianName)) {
                    Toast.makeText(getApplicationContext(), "Enter Guardian Full Name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(dGuardianNum) && dGuardianNum.length()>11 ) {
                    Toast.makeText(getApplicationContext(), "Enter the Appropiate Guardian Mobile Number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = firestore.collection("users").document(userID);
                Map<String,Object> updateUser= new HashMap<>();
                updateUser.put("full_name",dName);
                updateUser.put("age",dAge);
                updateUser.put("mbl_num",dNum);
                updateUser.put("guardian_name",dGuardianName);
                updateUser.put("guardian_num",dGuardianNum);

                documentReference.update(updateUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "User Profile Updated.",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

    }
}