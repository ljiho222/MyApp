package com.example.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CustomViewHolder> {

    private ArrayList<Comment> arrayList;
    private User user;
    private String articleID,userName,tempUserName;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public CommentAdapter(ArrayList<Comment> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CommentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //login된 user
        userName=((MainActivity)MainActivity.context).userName;
        articleID = ((CommentActivity)CommentActivity.context).articleID;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_comment, parent, false);
        CommentAdapter.CustomViewHolder holder = new CommentAdapter.CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CustomViewHolder holder, int position) {

        Comment comment = arrayList.get(position);

        //유저 닉네임, 댓글 내용
        if(arrayList.get(position).getUserName().equals(userName)) {
            holder.textViewUserName.setText(comment.getUserName() + "(작성자)");
        }

        else holder.textViewUserName.setText(comment.getUserName());

        tempUserName = comment.getUserName();
        holder.textViewContent.setText(comment.getContent());

        //삭제
        addDelete(holder, comment);

        //날짜
        addDate(holder, comment);


    }

    //삭제
    private void addDelete(@NonNull CommentAdapter.CustomViewHolder holder, final Comment comment) {
        Log.e("댓글 삭제",tempUserName+"  "+userName);
        if(tempUserName.equals(userName)) {
            holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("댓글 삭제")
                            .setMessage("삭제 하시겠습니까?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    databaseReference.child("Articles").child(articleID).child("Comments").child(comment.getCommentID()).removeValue();
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
        }
    }
    //날짜
    private void addDate(@NonNull CommentAdapter.CustomViewHolder holder, final Comment comment) {
        String date = comment.getEndDate();
        holder.textViewEndDate.setText(date.substring(0,4)+"년 "+date.substring(4,6) + "월 " + date.substring(6,8) +"일 ");
    }


    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected LinearLayout linearDelete;
        protected TextView textViewUserName, textViewContent, textViewEndDate;
        protected Button buttonUser, buttonDelete;
        protected ImageView imageView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.linearDelete = (LinearLayout)itemView.findViewById(R.id.linearDelete);

            this.textViewUserName = (TextView)itemView.findViewById(R.id.textViewUserName);
            this.textViewContent = (TextView)itemView.findViewById(R.id.textViewContent);
            this.textViewEndDate = (TextView)itemView.findViewById(R.id.textViewEndDate);

            this.buttonUser = (Button)itemView.findViewById(R.id.buttonUser);
            this.buttonDelete = (Button)itemView.findViewById(R.id.buttonDelete);

            this.imageView = (ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}