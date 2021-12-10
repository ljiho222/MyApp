package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CommentActivity extends AppCompatActivity {
    public static Object context;
    public User user;

    //게시글 user, article
    String userName,articleID;
    String imgUrl="";
    Integer tempNum=0;

    //user
    String userInfo="";

    //firebase
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    //article
    private TextView textViewPetAge,textViewPetSex,textViewPetName;
    private TextView textViewContent,textViewTitle,textViewCommentNum;
    private TextView textViewUser,textViewDate;
    private ImageView imageView;


    //comment
    private RecyclerView recyclerViewComment;
    private EditText editTextWriteComment;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;

    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Comment> arrayList;
    private CommentAdapter commentAdapter;

    private static SimpleDateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //user,article info
        Intent intent = getIntent();

        userName=intent.getStringExtra("userName");//게시글 작성자 이름
        articleID=intent.getStringExtra("articleID");
        userInfo=intent.getStringExtra("userInfo");//user 정보

        Log.e("####",userInfo);
        context = this;


        initView();
        setContent();

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setContent();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView() {
        //content
        textViewPetAge=(TextView)findViewById(R.id.textViewPetAge);
        textViewPetSex=(TextView)findViewById(R.id.textViewPetSex);
        textViewPetName=(TextView)findViewById(R.id.textViewPetName);

        textViewContent=(TextView)findViewById(R.id.textViewContent);
        textViewTitle=(TextView)findViewById(R.id.textViewTitle);

        textViewUser=(TextView)findViewById(R.id.textViewUser);
        textViewDate=(TextView)findViewById(R.id.textViewDate);
        imageView=(ImageView)findViewById(R.id.imageView);

        //comment
        recyclerViewComment = (RecyclerView)findViewById(R.id.recyclerViewComment);
        editTextWriteComment=(EditText)findViewById(R.id.editTextWriteComment);
        textViewCommentNum=(TextView)findViewById(R.id.textViewCommentNum);


        //layout
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerViewComment.setLayoutManager(linearLayoutManager);

        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item ){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static String getCurrentDate(){
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    private void setContent() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("정보를 불러오는 중입니다");
        progressDialog.show();

        arrayList = new ArrayList<>();
        commentAdapter = new CommentAdapter(arrayList);
        recyclerViewComment.setAdapter(commentAdapter);

        databaseReference.child("Articles").child(articleID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Article article = snapshot.getValue(Article.class);

                //pet info
                textViewPetName.setText(article.getName()+"/");
                textViewPetSex.setText(article.getSex()+"/");
                textViewPetAge.setText(article.getAge()+"살");

                textViewContent.setText(article.getContent());
                textViewTitle.setText(article.getTitle());

                textViewUser.setText(article.getUserName());

                String tempDate=article.getEndDate();
                textViewDate.setText(tempDate.substring(0,4)+"년 "+tempDate.substring(4,6)+"월 "+tempDate.substring(6,8)+"일");

                imgUrl=article.getImage();
               //Log.e("##","imgUrl: "+ imgUrl);
                Glide.with(CommentActivity.this).load(imgUrl).into(imageView);

                viewComments();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                textViewContent.setText("정보를 불러오지 못했습니다.");

            }
        });
    }

    private void viewComments(){
        tempNum=0;
        databaseReference.child("Articles").child(articleID).child("Comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Comment comment = snapshot.getValue(Comment.class);
                    arrayList.add(comment);
                    tempNum++;
                }
                commentAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                //Log.e("##", String.valueOf(tempNum));
                textViewCommentNum.setText(String.valueOf(tempNum));

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    public void onClickComment(View v){

        String textComment = editTextWriteComment.getText().toString();
        if(textComment.equals("")){
            ToastText("내용을 입력해주세요.");
            return;
        }

        editTextWriteComment.setText("");


        Long now = System.currentTimeMillis();
        Comment comment = new Comment(Long.toString(now),userInfo,textComment,getCurrentDate());
        databaseReference.child("Articles").child(articleID).child("Comments").child(Long.toString(now)).setValue(comment);
        ToastText("작성 완료되었습니다.");

        setContent();
    }

    private void ToastText(String text) {
        Toast.makeText(CommentActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}