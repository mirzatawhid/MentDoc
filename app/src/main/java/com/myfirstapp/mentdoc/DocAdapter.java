package com.myfirstapp.mentdoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    }

    @Override
    public int getItemCount() {
        return docDetailsArrayList.size();
    }

    public static class DocViewHolder extends RecyclerView.ViewHolder {

        TextView docName, docPost, docTiming;
        ImageView docImage;

        public DocViewHolder(@NonNull View itemView) {
            super(itemView);

            docName = itemView.findViewById(R.id.item_name);
            docPost = itemView.findViewById(R.id.item_position);
            docTiming = itemView.findViewById(R.id.item_timing);
            docImage = itemView.findViewById(R.id.item_pic);

        }
    }
}