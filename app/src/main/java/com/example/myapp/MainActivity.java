package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    //userInfo
    String userName,userID;

    //firebase
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    //adapter에서 ref
    public static Object context;

    //View
    private FragmentManager fragmentManager;
    private Main_fragment fragmentMain;
    private Animal_fragment fragmentAni;
    private Health_fragment fragmentHel;
    private Hos_fragment fragmentHos;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();


        //user 정보
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        userID = intent.getStringExtra("userID");


        //기본 fragment set
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout, fragmentMain).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.btn_main:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Main_fragment()).commit();
                        break;

                    case R.id.btn_ani:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Animal_fragment()).commit();

                        break;

                    case R.id.btn_health:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Health_fragment()).commit();

                        break;

                    case R.id.btn_hos:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Hos_fragment()).commit();
                        break;

                }
                return true;
            }
        });

    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottomnavi);

        context = this;
        fragmentMain = new Main_fragment();
        fragmentAni = new Animal_fragment();
        fragmentHel = new Health_fragment();
        fragmentHos = new Hos_fragment();
    }

   /* private void initFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//로그인한 user 정보
        String uid = user.getUid();//user 고유 id
        mAuth = FirebaseAuth.getInstance(); //user 계정 정보

    }*/
}