package com.myfirstapp.mentdoc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class DocAdapter extends RecyclerView.Adapter<DocAdapter.DocViewHolder> {

    Context context;
    ArrayList<docDetails> docDetailsArrayList;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public DocAdapter(Context context, ArrayList<docDetails> docDetailsArrayList) {
        this.context = context;
        this.docDetailsArrayList = docDetailsArrayList;
    }


    @NonNull
    @Override
    public DocAdapter.DocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.doctor_item, parent, false);
        return new DocViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DocAdapter.DocViewHolder holder, int position) {

        docDetails docDetails = docDetailsArrayList.get(position);
        holder.docName.setText(docDetails.getName());
        holder.docPost.setText(docDetails.getPost());
        holder.docTiming.setText(docDetails.getTiming());
        holder.docRating.setText(docDetails.getRating());


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("appointments")
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Access document data using document.getData()
                            Map<String, Object> data = document.getData();
                            if (document.get("doc_id").equals(docDetails.getId())){
                                holder.btn.setClickable(false);
                                holder.btn.setBackgroundResource(R.drawable.custom_btn_unavailable);
                                holder.btn.setTextColor(R.color.black);
                            }
                        }
                    }
                });


        Picasso.get().load(docDetails.getImage()).into(holder.docImage);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,ConfirmBookingActivity.class);
                intent.putExtra("doc_details",docDetails);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return docDetailsArrayList.size();
    }

    public static class DocViewHolder extends RecyclerView.ViewHolder {

        TextView docName, docPost, docTiming,btn,docRating;
        ImageView docImage;

        public DocViewHolder(@NonNull View itemView) {
            super(itemView);

            docName = itemView.findViewById(R.id.item_name);
            docPost = itemView.findViewById(R.id.item_date);
            docTiming = itemView.findViewById(R.id.item_timing);
            docImage = itemView.findViewById(R.id.item_pic);
            btn = itemView.findViewById(R.id.item_btn);
            docRating = itemView.findViewById(R.id.item_rating);



        }
    }
}