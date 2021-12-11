package com.example.myapp;

import android.app.Activity;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class PainAdapter extends RecyclerView.Adapter<PainAdapter.CustomViewHolder> implements Filterable {
    private ArrayList<Pain> arrayList;
    private ArrayList<Pain> unfilteredList;


    public PainAdapter(ArrayList<Pain> arrayList) {
        this.arrayList = arrayList;
        unfilteredList = arrayList;

    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView textViewContent,textViewPainName;
        protected ImageView imageView;
        protected CardView mView;

        public CustomViewHolder(View itemView) {
            super(itemView);

            this.textViewPainName=(TextView)itemView.findViewById(R.id.textViewPainName);
            this.textViewContent=(TextView)itemView.findViewById(R.id.textViewContent);
            this.imageView=(ImageView)itemView.findViewById(R.id.imageView);
            this.mView=(CardView)itemView.findViewById(R.id.mArticleView);

        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pain, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Pain pain = arrayList.get(position);

        //이름,내용
        holder.textViewPainName.setText(pain.getName());
        holder.textViewContent.setText(pain.getContent());


        //사진
        StorageReference storageReference =((DogHealthActivity)DogHealthActivity.context).storageReference;
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri.toString()).into(holder.imageView);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    arrayList=unfilteredList;
                } else {
                    ArrayList<Pain> filteringList = new ArrayList<>();
                    for(Pain item : unfilteredList) {
                        if(item.getContent().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(item);
                        }
                    }
                    arrayList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<Pain>)results.values;
                notifyDataSetChanged();
            }
        };
    }


    
  
}
