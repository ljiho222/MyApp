package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<PetItem> mList;
    private LayoutInflater mInflate;
    private Context context;


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView happenPlacetxt,kindCdtxt,sexCdtxt;
        private ImageView popfileimg;
        public CustomViewHolder(View view) {
            super(view);
            this.happenPlacetxt = (TextView) view.findViewById(R.id.happenPlace);
            this.kindCdtxt = (TextView) view.findViewById(R.id.kindCd);
            this.sexCdtxt = (TextView) view.findViewById(R.id.sexCd);
            this.popfileimg = (ImageView) view.findViewById(R.id.popfile);
        }
    }


    public CustomAdapter(Context context, ArrayList<PetItem> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = mInflate.inflate(R.layout.pet_infor_layout, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.happenPlacetxt.setText(mList.get(position).getHappenPlace());
        viewholder.kindCdtxt.setText(mList.get(position).getKindCd());
        viewholder.sexCdtxt.setText(mList.get(position).getSexCd());
        //viewholder.popfileimg.setImageResource(mList.get(position).getPopfile());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
