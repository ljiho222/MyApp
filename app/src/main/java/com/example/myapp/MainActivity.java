package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    private FragmentTransaction transaction;
    public static Stack<Fragment> fragmentStack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userName=intent.getStringExtra("userName");
        userID=intent.getStringExtra("userID");
        
        //user = (User)this.getIntent().getSerializableExtra("userInfo");
        //Log.e("###", userID+"__"+userName);

        context=this;

        fragmentMain= new Main_fragment();
        fragmentAni= new Animal_fragment();
        fragmentHel= new Health_fragment();
        fragmentHos= new Hos_fragment();

        bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("userID",userID);

        fragmentStack = new Stack<>();
        fragmentStack.push(fragmentMain);

        fragmentManager = getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout,fragmentMain).commitAllowingStateLoss();
        fragmentMain.setArguments(bundle);


        //Log.e("##","main");
    }

    public void OnClick(View view) {
        transaction=fragmentManager.beginTransaction();
        //Log.e("###", String.valueOf(bundle));

        switch (view.getId()){
            case R.id.btn_main:
                fragmentMain.setArguments(bundle);
                transaction.replace(R.id.framelayout, fragmentMain).commitAllowingStateLoss();
                break;

            case R.id.btn_ani:
                fragmentAni.setArguments(bundle);
                transaction.replace(R.id.framelayout, fragmentAni).commitAllowingStateLoss();
                break;

            case R.id.btn_health:
                fragmentHel.setArguments(bundle);
                transaction.replace(R.id.framelayout, fragmentHel).commitAllowingStateLoss();
                break;

            case R.id.btn_hos:
                fragmentHos.setArguments(bundle);
                transaction.replace(R.id.framelayout, fragmentHos).commitAllowingStateLoss();
                break;

        }
    }
    public void onBackPressed(){
        if(!fragmentStack.isEmpty()){
            Fragment nextFragment = fragmentStack.pop();
            fragmentManager.beginTransaction().replace(R.id.framelayout, nextFragment).commit();
            System.out.println("[TESTING >>] " + fragmentStack.size());
        }else {
            super.onBackPressed();
        }
    }
}