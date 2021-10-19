package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    private Main_fragment fragmentMain;
    private Animal_fragment fragmentAni;
    private Health_fragment fragmentHel;
    private Hos_fragment fragmentHos;

    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        fragmentMain= new Main_fragment();
        fragmentAni= new Animal_fragment();
        fragmentHel= new Health_fragment();
        fragmentHos= new Hos_fragment();

        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout,fragmentMain).commitAllowingStateLoss();
    }

    public void OnClick(View view) {
        transaction=fragmentManager.beginTransaction();

        switch (view.getId()){
            case R.id.btn_main:
                transaction.replace(R.id.framelayout, fragmentMain).commitAllowingStateLoss();
                break;
            case R.id.btn_ani:
                transaction.replace(R.id.framelayout, fragmentAni).commitAllowingStateLoss();
                break;

            case R.id.btn_health:
                transaction.replace(R.id.framelayout, fragmentHel).commitAllowingStateLoss();
                break;

            case R.id.btn_hos:
                transaction.replace(R.id.framelayout, fragmentHos).commitAllowingStateLoss();
                break;

        }
    }
}