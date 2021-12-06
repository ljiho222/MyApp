package com.example.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.CustomViewHolder> implements Filterable {

    private ArrayList<Article> arrayList;
    private ArrayList<Article> unfilteredList;
    private String userinfo,userName;
    private User user;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public ArticleAdapter(ArrayList<Article> arrayList, String userinfo) {
        this.arrayList = arrayList;
        unfilteredList = arrayList;

        this.userinfo = userinfo;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        //login된 user

        userName=((MainActivity)MainActivity.context).userName;

        Log.e("######",userName);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_article, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Article article = arrayList.get(position);

        //유저 닉네임, 게시글 내용
        holder.textViewUserName.setText(article.getUserName());
        holder.textViewContent.setText(article.getContent());
        holder.textViewName.setText(article.getName());
        holder.textViewTitle.setText(article.getTitle());


        // 이미지 추가=> 맞나
        if(!article.getImage().equals("")) {
            //Log.e("arrayList",""+"실행됨");
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext()).load(article.getImage()).into(holder.imageView);
        }
        else{
            holder.imageView.setVisibility(View.GONE);
        }


        //삭제
        addDelete(holder, article);

        //날짜
        addDate(holder, article);

        //뷰클릭
        clickItem(holder,article);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) holder.itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        deviceWidth = deviceWidth - 50;
        int deviceHeight = (int) (deviceWidth * 0.5);
        holder.mView.getLayoutParams().width=deviceWidth;
        holder.mView.getLayoutParams().height=deviceHeight;
    }


    //아이템 클릭
    private void clickItem(@NonNull CustomViewHolder holder, final Article article) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommentActivity.class);
                intent.putExtra("userName", article.getUserName());
                intent.putExtra("articleID", article.getArticleID());
                intent.putExtra("userInfo",userName);
                v.getContext().startActivity(intent);
            }
        });
    }

    //삭제
    private void addDelete(@NonNull CustomViewHolder holder, final Article article) {
        Log.e("addDelete", String.valueOf(article.getUserID().equals(userName)));
        if(article.getUserName().equals(userName)) {
            holder.buttonDelete.setVisibility(View.VISIBLE);
            holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("게시글 삭제")
                            .setMessage("삭제 하시겠습니까?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    databaseReference.child("Articles").child(article.getArticleID()).removeValue();
                                    Toast.makeText(v.getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(v.getContext(), "취소하였습니다", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            });
        }
        else {
            holder.buttonDelete.setVisibility(View.GONE);
            // holder.linearDelete.removeAllViews();
        }
    }

    //날짜
    private void addDate(@NonNull CustomViewHolder holder, final Article article) {
        String date = article.getEndDate();
        holder.textViewEndDate.setText(date.substring(0,4) + "년 " +date.substring(4,6) + "월 " + date.substring(6,8) +"일 ");
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
                    ArrayList<Article> filteringList = new ArrayList<>();
                    for(Article item : unfilteredList) {
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
                arrayList = (ArrayList<Article>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected LinearLayout linearDelete;
        protected TextView textViewUserName, textViewContent, textViewEndDate;
        protected TextView textViewTitle,textViewName;
        protected Button buttonUser, buttonDelete;
        protected ImageView imageView;
        protected CardView mView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.linearDelete = (LinearLayout)itemView.findViewById(R.id.linearDelete);

            this.textViewUserName = (TextView)itemView.findViewById(R.id.textViewUserName);
            this.textViewContent = (TextView)itemView.findViewById(R.id.textViewContent);
            this.textViewName=(TextView)itemView.findViewById(R.id.textViewName);
            this.textViewTitle=(TextView)itemView.findViewById(R.id.textViewTitle);
            this.textViewEndDate = (TextView)itemView.findViewById(R.id.textViewEndDate);
            this.buttonDelete = itemView.findViewById(R.id.buttonDelete);


            this.imageView = (ImageView)itemView.findViewById(R.id.imageView);
            this.mView=(CardView)itemView.findViewById(R.id.mArticleView);
        }
    }
}