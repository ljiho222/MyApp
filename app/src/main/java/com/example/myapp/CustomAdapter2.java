package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.CustomViewHolder> {
    private ArrayList<HosItem> mList;
    private LayoutInflater mInflate;
    private Context context;

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView apiNewAddresstxt, apiTeltxt, apiDongNametxt;

        public CustomViewHolder(View view) {
            super(view);
            this.apiNewAddresstxt = (TextView) view.findViewById(R.id.apiNewAddress);
            this.apiTeltxt = (TextView) view.findViewById(R.id.apiTel);
            this.apiDongNametxt = (TextView) view.findViewById(R.id.apiDongName);
            //this.teltxt = (TextView) view.findViewById(R.id.tel);
        }
    }


    public CustomAdapter2(Context context, ArrayList<HosItem> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = mInflate.inflate(R.layout.hos_infor_layout, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

       //viewholder.guguntxt.setText(mList.get(position).getGugun());
        viewholder.apiNewAddresstxt.setText(mList.get(position).getApiNewAddress());
        viewholder.apiDongNametxt.setText(mList.get(position).getApiDongName());
        viewholder.apiTeltxt.setText(mList.get(position).getApiTel());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
