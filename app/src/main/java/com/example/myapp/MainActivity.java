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

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    public static Object context;
    String userName,userID;
    private Bundle bundle;
    private FragmentManager fragmentManager;
    private Main_fragment fragmentMain;
    private Animal_fragment fragmentAni;
    private Health_fragment fragmentHel;
    private Hos_fragment fragmentHos;
    private BottomNavigationView bottomNavigationView;

    private FragmentTransaction transaction;
    public static Stack<Fragment> fragmentStack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomnavi);

        //user 정보
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        userID = intent.getStringExtra("userID");

        //user = (User)this.getIntent().getSerializableExtra("userInfo");
        //Log.e("###", userID+"__"+userName);

        context = this;

        fragmentMain = new Main_fragment();
        fragmentAni = new Animal_fragment();
        fragmentHel = new Health_fragment();
        fragmentHos = new Hos_fragment();

        //user 정보
        bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("userID", userID);

        fragmentStack = new Stack<>();
        fragmentStack.push(fragmentMain);

        //기본 fragment set
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout, fragmentMain).commitAllowingStateLoss();
        fragmentMain.setArguments(bundle);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.btn_main:
                        Log.e("##",bundle.getString(userID)+"여기"+bundle.getString(userName));
                        fragmentMain.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Main_fragment()).commit();
                        break;

                    case R.id.btn_ani:
                        fragmentAni.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Animal_fragment()).commit();

                        break;

                    case R.id.btn_health:
                        fragmentHel.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Health_fragment()).commit();

                        break;

                    case R.id.btn_hos:
                        fragmentHos.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Hos_fragment()).commit();
                        break;

                }
                return true;
            }
        });

    }
}