package com.myfirstapp.mentdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConfirmBookingActivity extends AppCompatActivity {

    boolean programmaticChange = false;

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    final Calendar cal = Calendar.getInstance();

    int mDate = cal.get(Calendar.DATE);
    int mMonth = cal.get(Calendar.MONTH);
    int mYear = cal.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        docDetails docDetails = (com.myfirstapp.mentdoc.docDetails) getIntent().getSerializableExtra("doc_details");

        RadioButton selectedRadioButton1 = findViewById(R.id.slot1_id);
        RadioButton selectedRadioButton2 = findViewById(R.id.slot2_id);
        RadioButton selectedRadioButton3 = findViewById(R.id.slot3_id);
        RadioButton selectedRadioButton4 = findViewById(R.id.slot4_id);
        RadioButton selectedRadioButton5 = findViewById(R.id.slot5_id);
        RadioButton selectedRadioButton6 = findViewById(R.id.slot6_id);

        TextView dateEditText = findViewById(R.id.dateeditText);
        ImageView dateImageView = findViewById(R.id.date_image);

        dateEditText.setText(String.valueOf(mDate)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mYear));

        dateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(ConfirmBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateEditText.setText(String.valueOf(date)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year));
                        DatabaseReference db = FirebaseDatabase.getInstance("https://mentdoc-da69c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                        db.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                selectedRadioButton1.setEnabled(true);
                                selectedRadioButton2.setEnabled(true);
                                selectedRadioButton3.setEnabled(true);
                                selectedRadioButton4.setEnabled(true);
                                selectedRadioButton5.setEnabled(true);
                                selectedRadioButton6.setEnabled(true);
                                selectedRadioButton1.setBackgroundResource(R.drawable.custom_slot_selector);
                                selectedRadioButton2.setBackgroundResource(R.drawable.custom_slot_selector);
                                selectedRadioButton3.setBackgroundResource(R.drawable.custom_slot_selector);
                                selectedRadioButton4.setBackgroundResource(R.drawable.custom_slot_selector);
                                selectedRadioButton5.setBackgroundResource(R.drawable.custom_slot_selector);
                                selectedRadioButton6.setBackgroundResource(R.drawable.custom_slot_selector);

                                for (DataSnapshot childSnapshot : snapshot.child("appointments").child(dateEditText.getText().toString()).child(docDetails.getId()).getChildren()) {
                                    String childKey = childSnapshot.getKey();
                                    Log.d("slotNum", "onDataChange: " + childKey);
                                    if (childSnapshot.exists()) {
                                        if (childKey.equals("slot1")) {
                                            selectedRadioButton1.setEnabled(false);
                                            selectedRadioButton1.setBackgroundResource(R.drawable.custom_slot_unavailable);
                                        } else if (childKey.equals("slot2")) {
                                            selectedRadioButton2.setEnabled(false);
                                            selectedRadioButton2.setBackgroundResource(R.drawable.custom_slot_unavailable);
                                        } else if (childKey.equals("slot3")) {
                                            selectedRadioButton3.setEnabled(false);
                                            selectedRadioButton3.setBackgroundResource(R.drawable.custom_slot_unavailable);
                                        } else if (childKey.equals("slot4")) {
                                            selectedRadioButton4.setEnabled(false);
                                            selectedRadioButton4.setBackgroundResource(R.drawable.custom_slot_unavailable);
                                        } else if (childKey.equals("slot5")) {
                                            selectedRadioButton5.setEnabled(false);
                                            selectedRadioButton5.setBackgroundResource(R.drawable.custom_slot_unavailable);
                                        } else if (childKey.equals("slot6")) {
                                            selectedRadioButton6.setEnabled(false);
                                            selectedRadioButton6.setBackgroundResource(R.drawable.custom_slot_unavailable);
                                        }
                                    }else{
                                        selectedRadioButton1.setEnabled(true);
                                        selectedRadioButton2.setEnabled(true);
                                        selectedRadioButton3.setEnabled(true);
                                        selectedRadioButton4.setEnabled(true);
                                        selectedRadioButton5.setEnabled(true);
                                        selectedRadioButton6.setEnabled(true);
                                        selectedRadioButton1.setBackgroundResource(R.drawable.custom_slot_selector);
                                        selectedRadioButton2.setBackgroundResource(R.drawable.custom_slot_selector);
                                        selectedRadioButton3.setBackgroundResource(R.drawable.custom_slot_selector);
                                        selectedRadioButton4.setBackgroundResource(R.drawable.custom_slot_selector);
                                        selectedRadioButton5.setBackgroundResource(R.drawable.custom_slot_selector);
                                        selectedRadioButton6.setBackgroundResource(R.drawable.custom_slot_selector);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("slotNum", "onCancelled: Failed");
                            }
                        });
                    }
                },mYear,mMonth,mDate);
                datePickerDialog.show();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        TextView docName = findViewById(R.id.doc_name);
        TextView docPost = findViewById(R.id.doc_post);
        TextView docTiming = findViewById(R.id.doc_timing);

        Button bookingBtn = findViewById(R.id.bookingBtn);


        selectedRadioButton1.setText("Slot-1\n"+docDetails.getSlot1());
        selectedRadioButton2.setText("Slot-2\n"+docDetails.getSlot2());
        selectedRadioButton3.setText("Slot-3\n"+docDetails.getSlot3());
        selectedRadioButton4.setText("Slot-4\n"+docDetails.getSlot4());
        selectedRadioButton5.setText("Slot-5\n"+docDetails.getSlot5());
        selectedRadioButton6.setText("Slot-6\n"+docDetails.getSlot6());

        RadioGroup radioGroup1 = findViewById(R.id.slot_group1);
        RadioGroup radioGroup2 = findViewById(R.id.slot_group2);

//        DatabaseReference db = FirebaseDatabase.getInstance("https://mentdoc-da69c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
//        db.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot childSnapshot : snapshot.child("appointments").child(dateEditText.getText().toString()).child(docDetails.getId()).getChildren()) {
//                    String childKey = childSnapshot.getKey();
//                    Log.d("slotNum", "onDataChange: "+childKey);
//                    if (childKey.equals("slot1")) {
//                        selectedRadioButton1.setEnabled(false);
//                        selectedRadioButton1.setBackgroundResource(R.drawable.custom_slot_unavailable);
//                    }else if (childKey.equals("slot2")){
//                        selectedRadioButton2.setEnabled(false);
//                        selectedRadioButton2.setBackgroundResource(R.drawable.custom_slot_unavailable);
//                    }else if (childKey.equals("slot3")){
//                        selectedRadioButton3.setEnabled(false);
//                        selectedRadioButton3.setBackgroundResource(R.drawable.custom_slot_unavailable);
//                    }else if (childKey.equals("slot4")){
//                        selectedRadioButton4.setEnabled(false);
//                        selectedRadioButton4.setBackgroundResource(R.drawable.custom_slot_unavailable);
//                    }else if (childKey.equals("slot5")){
//                        selectedRadioButton5.setEnabled(false);
//                        selectedRadioButton5.setBackgroundResource(R.drawable.custom_slot_unavailable);
//                    }else if (childKey.equals("slot6")){
//                        selectedRadioButton6.setEnabled(false);
//                        selectedRadioButton6.setBackgroundResource(R.drawable.custom_slot_unavailable);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("slotNum", "onCancelled: Failed");
//            }
//        });

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

                //saving data in realtime database
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


                databaseReference.child("appointments").child(dateEditText.getText().toString()).child(docDetails.getId()).child(selectedSlot).setValue(appointmentData).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                //saving data on user database
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String,Object> appointmentUserData= new HashMap<>();
                appointmentUserData.put("date",dateEditText.getText().toString());
                appointmentUserData.put("doc_id",docDetails.getId());
                appointmentUserData.put("slot",selectedSlot);

                db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("appointments")
                        .add(appointmentUserData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("appointmentData", "onSuccess: Appointment Data Stored Successfully!");
                            }
                        });

            }
        });



        //back button functionality
        ImageView backBtn = findViewById(R.id.bookingBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}