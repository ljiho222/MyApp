package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    // widgets
    private TextView textMessage;
    private Button btnSignin, btnSignup, btnFind;
    private User userinfo;

    private TextInputLayout input_id_layout,input_pw_layout;

    // Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    // If auto-login is needed
    private SharedPreferences preferences;

    // variables
    private String loginId,loginPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initFirebase();
        initView();
        initAccount();
    }

    public void initFirebase(){
        // Firebase
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference("Users");

        //SharedPreference
        preferences=getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        loginId=preferences.getString("inputId",null);
        loginPw=preferences.getString("inputPw",null);
    }

    public void initAccount(){
        if(mAuth.getCurrentUser()!=null){
            mAuth.signOut();
        }
        else if (loginId != null && loginPw != null) {
            loginAccount(loginId, loginPw);
        }
    }

    public void initView(){
        // Initialize widget

        btnSignin=(Button)findViewById(R.id.btnSignin);
        btnSignup=(Button)findViewById(R.id.btnSignup);
        btnFind=(Button)findViewById(R.id.btnFind);
        textMessage=(TextView)findViewById(R.id.textMessage);

        input_id_layout=(TextInputLayout)findViewById(R.id.input_id_layout);
        input_pw_layout=(TextInputLayout)findViewById(R.id.input_pw_layout);

        // SignIn
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=input_id_layout.getEditText().getText().toString().trim();
                String pw=input_pw_layout.getEditText().getText().toString().trim();

                //Log.e("##",email+" "+pw);

                if(email.equals("")){
                    textMessage.setText("???????????? ???????????????");
                }
                else if(pw.equals("")){
                    textMessage.setText("??????????????? ???????????????");
                }
                else{
                    textMessage.setText("");
                    loginAccount(email,pw);
                }
            }
        });

        // SIgnUp
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Find pw
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,FindpwActivity.class);
                startActivity(intent);
            }
        });
    }

    // Login Method
    private void loginAccount(final String email, final String pw){
        mAuth.signInWithEmailAndPassword(email,pw)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            showToast("????????? ??????");
                            setAutoLogin(email,pw);
                            FirebaseUser user=mAuth.getCurrentUser();
                            updateUIwithEmailCheck(user);
                        }
                        else{
                            showToast("???????????? ??????????????? ??????????????????");
                        }
                    }
                });
    }

    // Auto Login Method
    private void setAutoLogin(String email,String pw){
        SharedPreferences.Editor autoLogin=preferences.edit();
        autoLogin.putString("inputId",email);
        autoLogin.putString("inputPw",pw);
        autoLogin.commit();
    }
    //category reset & skip
    private void updateUIwithEmailCheck(final FirebaseUser user){
        if(user!=null){
            boolean emailVerified=user.isEmailVerified();
            if(emailVerified){
                mRef = mRef.child(user.getUid());
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User userInfo = dataSnapshot.getValue(User.class);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        //Log.e("login",user.getUid());

                        intent.putExtra("userID", user.getUid());
                        intent.putExtra("userName", userInfo.getUserName());

                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            else{
                mAuth.signOut();
                showToast("????????? ?????? ??? ????????? ??? ????????????");
            }
        }
    }

    public void showToast(String message){
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
    }


    // If needed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}