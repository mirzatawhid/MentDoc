package com.myfirstapp.mentdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConfirmBookingActivity extends AppCompatActivity {

    boolean programmaticChange = false;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        mAuth = FirebaseAuth.getInstance();

        docDetails docDetails = (com.myfirstapp.mentdoc.docDetails) getIntent().getSerializableExtra("doc_details");

        TextView docName = findViewById(R.id.doc_name);
        TextView docPost = findViewById(R.id.doc_post);
        TextView docTiming = findViewById(R.id.doc_timing);

        Button bookingBtn = findViewById(R.id.bookingBtn);

        RadioButton selectedRadioButton1 = findViewById(R.id.slot1_id);
        RadioButton selectedRadioButton2 = findViewById(R.id.slot2_id);
        RadioButton selectedRadioButton3 = findViewById(R.id.slot3_id);
        RadioButton selectedRadioButton4 = findViewById(R.id.slot4_id);
        RadioButton selectedRadioButton5 = findViewById(R.id.slot5_id);
        RadioButton selectedRadioButton6 = findViewById(R.id.slot6_id);

        selectedRadioButton1.setText("Slot-1\n"+docDetails.getSlot1());
        selectedRadioButton2.setText("Slot-2\n"+docDetails.getSlot2());
        selectedRadioButton3.setText("Slot-3\n"+docDetails.getSlot3());
        selectedRadioButton4.setText("Slot-4\n"+docDetails.getSlot4());
        selectedRadioButton5.setText("Slot-5\n"+docDetails.getSlot5());
        selectedRadioButton6.setText("Slot-6\n"+docDetails.getSlot6());

        RadioGroup radioGroup1 = findViewById(R.id.slot_group1);
        RadioGroup radioGroup2 = findViewById(R.id.slot_group2);

// Flag to track if the change in checked state is programmatically triggered


// Set OnCheckedChangeListener for RadioButtons in the first RadioGroup
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!programmaticChange) {
                    // Set the flag to true to prevent recursive calls
                    programmaticChange = true;
                    // Uncheck all RadioButtons in the second RadioGroup
                    radioGroup2.clearCheck();
                    // Reset the flag after the operation
                    programmaticChange = false;
                }
            }
        });

// Set OnCheckedChangeListener for RadioButtons in the second RadioGroup
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!programmaticChange) {
                    // Set the flag to true to prevent recursive calls
                    programmaticChange = true;
                    // Uncheck all RadioButtons in the first RadioGroup
                    radioGroup1.clearCheck();
                    // Reset the flag after the operation
                    programmaticChange = false;
                }
            }
        });
        docName.setText(docDetails.getName());
        docPost.setText(docDetails.getPost());
        docTiming.setText(docDetails.getTiming());

        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int selectedRadioButtonId = radioGroup1.getCheckedRadioButtonId();
//                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
//
//                // If no RadioButton is selected, display a message and return
//                if (selectedRadioButton == null) {
//                    selectedRadioButtonId = radioGroup2.getCheckedRadioButtonId();
//                    selectedRadioButton = findViewById(selectedRadioButtonId);
//                    if (selectedRadioButton == null){
//                        Toast.makeText(ConfirmBookingActivity.this, "Select an Slot.", Toast.LENGTH_SHORT).show();
//                    }
//                }

                String selectedTimeSlot=null;
                String selectedSlot = null;
                
                if (selectedRadioButton1.isChecked()) {
                    selectedTimeSlot = docDetails.getSlot1();
                    selectedSlot = "slot1";
                } else if (selectedRadioButton2.isChecked()) {
                    selectedTimeSlot = docDetails.getSlot2();
                    selectedSlot = "slot2";
                } else if (selectedRadioButton3.isChecked()) {
                    selectedTimeSlot = docDetails.getSlot3();
                    selectedSlot = "slot3";
                }else if (selectedRadioButton4.isChecked()) {
                    selectedTimeSlot = docDetails.getSlot4();
                    selectedSlot = "slot4";
                }else if (selectedRadioButton5.isChecked()) {
                    selectedTimeSlot = docDetails.getSlot5();
                    selectedSlot = "slot5";
                }else if (selectedRadioButton6.isChecked()) {
                    selectedTimeSlot = docDetails.getSlot6();
                    selectedSlot = "slot6";
                } else {
                    Toast.makeText(ConfirmBookingActivity.this, "Select a Time Slot.", Toast.LENGTH_SHORT).show();
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mentdoc-da69c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                Map<String,Object> appointmentData= new HashMap<>();
                appointmentData.put("user_id",mAuth.getCurrentUser().getUid());
                appointmentData.put("time",selectedTimeSlot);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = sdf.format(new Date());

                databaseReference.child("appointments").child(currentDate).child(docDetails.getId()).child(selectedSlot).setValue(appointmentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ConfirmBookingActivity.this, "Appointment Booked Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ConfirmBookingActivity.this, "Something Went Wrong, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}