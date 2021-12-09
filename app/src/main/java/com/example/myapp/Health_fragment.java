package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Health_fragment extends Fragment {

    String userName,userID;

    //firebase
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    //view
    private ImageView dog_im,cat_im;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.activity_health_fragment,container,false);

        dog_im=(ImageView)view.findViewById(R.id.dog_im);
        cat_im=(ImageView)view.findViewById(R.id.cat_im);

        //user 정보
        if(getArguments()!=null){
            userName = getArguments().getString("userName");
            userID= getArguments().getString("userID");
        }

        dog_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DogHealthActivity.class);
                startActivity(intent);

            }
        });
        cat_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CatHealthActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
}