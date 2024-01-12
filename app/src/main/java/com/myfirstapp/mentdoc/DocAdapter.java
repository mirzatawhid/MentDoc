package com.myfirstapp.mentdoc;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DocAdapter extends RecyclerView.Adapter<DocAdapter.DocViewHolder> {

    Context context;
    ArrayList<docDetails> docDetailsArrayList;

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

        Picasso.get().load(docDetails.getImage()).into(holder.docImage);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.docName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_confirm_booking))
                        .setExpanded(true,1200)
                        .create();
                View view1 = dialogPlus.getHolderView();
                TextView docName = view1.findViewById(R.id.doc_name);
                TextView docPost = view1.findViewById(R.id.doc_post);
                TextView docTiming = view1.findViewById(R.id.doc_timing);

                docName.setText(docDetails.getName());
                docPost.setText(docDetails.getPost());
                docTiming.setText(docDetails.getTiming());
                dialogPlus.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return docDetailsArrayList.size();
    }

    public static class DocViewHolder extends RecyclerView.ViewHolder {

        TextView docName, docPost, docTiming,btn;
        ImageView docImage;

        public DocViewHolder(@NonNull View itemView) {
            super(itemView);

            docName = itemView.findViewById(R.id.item_name);
            docPost = itemView.findViewById(R.id.item_position);
            docTiming = itemView.findViewById(R.id.item_timing);
            docImage = itemView.findViewById(R.id.item_pic);
            btn = itemView.findViewById(R.id.item_btn);




        }
    }
}