package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class CommentActivity extends AppCompatActivity {
    //User
    String userName,articleID;

    String imgUrl="";

    //firebase
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private TextView textViewPetAge,textViewPetSex,textViewPetName;
    private TextView textViewContent,textViewTitle;
    private TextView textViewUser,textViewDate;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //user,article info
        Intent intent = getIntent();
        userName=intent.getStringExtra("userName");
        articleID=intent.getStringExtra("articleID");


        //content
        textViewPetAge=(TextView)findViewById(R.id.textViewPetAge);
        textViewPetSex=(TextView)findViewById(R.id.textViewPetSex);
        textViewPetName=(TextView)findViewById(R.id.textViewPetName);

        textViewContent=(TextView)findViewById(R.id.textViewContent);
        textViewTitle=(TextView)findViewById(R.id.textViewTitle);

        textViewUser=(TextView)findViewById(R.id.textViewUser);
        textViewDate=(TextView)findViewById(R.id.textViewDate);

        imageView=(ImageView)findViewById(R.id.imageView);


        setContent();

    }

    private void setContent() {

        databaseReference.child("Articles").child(articleID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Article article = snapshot.getValue(Article.class);

                //pet info
                textViewPetAge.setText(article.getAge());
                textViewPetSex.setText(article.getSex());
                textViewPetName.setText(article.getName());

                textViewContent.setText(article.getContent());
                textViewTitle.setText(article.getTitle());

                textViewUser.setText(article.getUserName());
                textViewDate.setText(article.getEndDate());

                imgUrl=article.getImage();
               //Log.e("##","imgUrl: "+ imgUrl);
                Glide.with(CommentActivity.this).load(imgUrl).into(imageView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}