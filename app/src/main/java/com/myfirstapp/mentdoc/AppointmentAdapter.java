package com.myfirstapp.mentdoc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>{

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Context context;
    ArrayList<appointmentDetails> appointmentDetailsArrayList;

    public AppointmentAdapter(Context context, ArrayList<appointmentDetails> appointmentDetailsArrayList) {
        this.context = context;
        this.appointmentDetailsArrayList = appointmentDetailsArrayList;
    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.appointment_item, parent, false);
        return new AppointmentAdapter.AppointmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointmentViewHolder holder, int position) {

        appointmentDetails details = appointmentDetailsArrayList.get(position);
        holder.docNameView.setText(details.getDocName());
        holder.dateView.setText(details.getDate());
        holder.timeView.setText(details.getSlot_time());
        holder.docPostview.setText(details.getDocPost());

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // Set dialog title, message, and other properties
                alertDialogBuilder.setTitle("Alert");
                alertDialogBuilder.setMessage("Are you sure to cancel the appointment?");
                alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);

                // Add positive button with its click listener
                alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mentdoc-da69c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                    databaseReference.child("appointments").child(details.getDate()).child(details.getDoc_id()).child(details.getSlot()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Booking Cancelled Successfully!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("appointments").document(details.getAppointment_id())
                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("cancel_booking", "onSuccess: Deleted from Firestore Successfully.");
                                }
                            });
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                });

                // Add negative button with its click listener
                alertDialogBuilder.setNegativeButton("No", (dialog, which) -> {
                    // Do something when the negative button is clicked
                    dialog.dismiss();
                });

                // Create and show the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return appointmentDetailsArrayList.size();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder{

        TextView docNameView,dateView,timeView,cancelBtn,docPostview;
        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            docNameView = itemView.findViewById(R.id.app_item_name);
            docPostview = itemView.findViewById(R.id.app_item_post);
            dateView = itemView.findViewById(R.id.app_item_date);
            timeView = itemView.findViewById(R.id.app_item_timing);
            cancelBtn = itemView.findViewById(R.id.app_item_btn);
        }
    }
}
