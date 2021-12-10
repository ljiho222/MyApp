package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class DogHealthActivity extends AppCompatActivity  {
    //firebase
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    //view
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private ScrollView scrollView;
    private TextView text1,text2,text3,text4,text5;
    private TextView textView1, textView2, textView3,textView4,textView5,textView6,textView7;

    String[] array={"예방의학","","","","","",""};
    String str="";

    //recyclerview
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Pain> arrayList;
    private PainAdapter painAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_health);

        initView();



        text1.setOnClickListener(listener);
        text2.setOnClickListener(listener);
        text3.setOnClickListener(listener);
        text4.setOnClickListener(listener);
        text5.setOnClickListener(listener);


        //기본값
        array = getResources().getStringArray(R.array.one);
        str="1";
        textSet();

        //set recyclerview to adapter
        painfunc(textView1);

    }

    private void initView() {
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);
        text4=(TextView)findViewById(R.id.text4);
        text5=(TextView)findViewById(R.id.text5);

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
    private void painfunc(TextView txt) {

       // Log.e("##","str="+str+"name"+txt.getText().toString());

        arrayList=new ArrayList<>();
        painAdapter=new PainAdapter(arrayList);

        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(painAdapter);

        databaseReference.child("Health").child("Dog").child(str).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Pain pain= snapshot.getValue(Pain.class);

                    arrayList.add(pain);

                }
                //Log.e("###", String.valueOf(arrayList.size()));
                Collections.reverse(arrayList);
                painAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("##","error");

            }
        });



    }
    View.OnClickListener listener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.text1:
                    array = getResources().getStringArray(R.array.one);
                    Log.e("###",array[0]);
                    str="1";
                    textSet();

                    break;

                case R.id.text2:
                    array = getResources().getStringArray(R.array.two);
                    Log.e("###",array[0]);
                    str="2";

                    textSet();

                    break;

                case R.id.text3:
                    array = getResources().getStringArray(R.array.three);
                    Log.e("###",array[0]);
                    str="3";

                    textSet();

                    break;

                case R.id.text4:
                    array = getResources().getStringArray(R.array.four);
                    Log.e("###",array[0]);
                    str="4";

                    textSet();

                    break;

                case R.id.text5:
                    array = getResources().getStringArray(R.array.five);
                    Log.e("###",array[0]);
                    str="5";

                    textSet();

                    break;

            }
        }

    };

    private void textSet() {
        //Log.e("#", String.valueOf(array.length));


        textView1=(TextView)findViewById(R.id.textView1);
        textView2=(TextView)findViewById(R.id.textView2);
        textView3=(TextView)findViewById(R.id.textView3);
        textView4=(TextView)findViewById(R.id.textView4);
        textView5=(TextView)findViewById(R.id.textView5);
        textView6=(TextView)findViewById(R.id.textView6);
        textView7=(TextView)findViewById(R.id.textView7);


        textView1.setText(array[0]);
        textView2.setText(array[1]);
        textView3.setText(array[2]);
        textView4.setText(array[3]);
        textView5.setText(array[4]);
        textView6.setText(array[5]);
        textView7.setText(array[6]);


        textView1.setOnClickListener(clickListner);
        textView2.setOnClickListener(clickListner);
        textView3.setOnClickListener(clickListner);
        textView4.setOnClickListener(clickListner);
        textView5.setOnClickListener(clickListner);
        textView6.setOnClickListener(clickListner);
        textView7.setOnClickListener(clickListner);

       /* for(int i=0;i<array.length;i++){
            //Log.e("##",array[i]);
            TextView txt = new TextView(getApplicationContext());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            txt.setLayoutParams(p);
            txt.setText(array[i]);
            txt.setTag(i);
            linearLayout.addView(txt);

            //txt.setOnClickListener(onClickListener);

        }*/


    }
    View.OnClickListener clickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.textView1:
                    painfunc(textView1);
                    break;
                case R.id.textView2:
                    painfunc(textView2);
                    break;
                case R.id.textView3:
                    painfunc(textView3);
                    break;
                case R.id.textView4:
                    painfunc(textView4);
                    break;
                case R.id.textView5:
                    painfunc(textView5);
                    break;
                case R.id.textView6:
                    painfunc(textView6);
                    break;
                case R.id.textView7:
                    painfunc(textView7);
                    break;


            }
        }
    };
}