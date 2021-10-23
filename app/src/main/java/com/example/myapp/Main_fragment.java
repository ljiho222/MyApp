package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ShareActionProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Main_fragment extends Fragment {

    String userName,userID;

    //firebase
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private User user;


    //view
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btn_add;


    //recyclerview
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Article> arrayList;
    private ArticleAdapter articleAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.activity_main_fragment,container,false);


        btn_add=(Button)view.findViewById(R.id.btn_add);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);

        //swipelayout
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //set recyclerview to adapter
        articlefunc();


        //user 정보
        Bundle bundle = getArguments();
        userName = bundle.getString("userName");
        userID= bundle.getString("userID");

        //article 추가시 화면 전환
        if(bundle!=null){
            Log.e("###", String.valueOf(bundle));
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),WriteActivity.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("userID",userID);
                    startActivity(intent);
                }
            });
        }




        return view;
    }

    private void articlefunc() {
        arrayList=new ArrayList<>();
        articleAdapter=new ArticleAdapter(arrayList,userID);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(articleAdapter);

        databaseReference.child("Articles").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Article article= snapshot.getValue(Article.class);
                    arrayList.add(article);

                }
                //Log.e("###", String.valueOf(arrayList.size()));
                Collections.reverse(arrayList);
                articleAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}