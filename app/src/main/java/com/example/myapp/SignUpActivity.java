package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    // widgets
    private EditText editId,editPw,editNickname;
    private TextView textMessage;
    private Button btnSignup;

    private TextInputLayout input_id_layout,input_name_layout,input_pw_layout;


    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initFirebase();
        initView();
    }

    public void initFirebase(){
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference("Users");
    }

    public void initView(){
        // Initialize widgets
        editId=(EditText)findViewById(R.id.editId);
        editPw=(EditText)findViewById(R.id.editPw);
        editNickname=(EditText)findViewById(R.id.editNickname);
        btnSignup=(Button)findViewById(R.id.btnSignup);
        textMessage=(TextView)findViewById(R.id.textMessage);

        input_id_layout=(TextInputLayout)findViewById(R.id.input_id_layout);
        input_name_layout=(TextInputLayout)findViewById(R.id.input_name_layout);
        input_pw_layout=(TextInputLayout)findViewById(R.id.input_pw_layout);

        // Signup
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=input_id_layout.getEditText().getText().toString().trim();
                String pw=input_pw_layout.getEditText().getText().toString().trim();
                String nickname=input_name_layout.getEditText().getText().toString().trim();

                if(email.equals("")){
                    textMessage.setText("???????????? ???????????????");
                }
                else if(pw.equals("")){
                    textMessage.setText("??????????????? ???????????????");
                }
                else if(nickname.equals("")){
                    textMessage.setText("???????????? ???????????????");
                }
                else{
                    textMessage.setText("");
                    createUser(email, pw, nickname);
                }
            }
        });
    }

    public void createUser(final String email, String pw, final String nickname) {
        mAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("TAG","create user success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        String uid = user.getUid();
                        mDatabase.getReference().child("Users").child(uid).setValue(new User(nickname, uid));
                        sendEmail(user);
                        updateUI(user);
                    } else {
                        Log.e("###",email+" "+pw+" "+nickname);
                        showToast("????????? ????????? ??? ??? ??????????????????");
                    }
                });
    }



    public void sendEmail(FirebaseUser user){
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                showToast("?????? ???????????? ??????????????????");
            }
            else{
                showToast("?????? ????????? ????????? ?????????????????????");
            }
        });
    }

    public void updateUI(FirebaseUser user){
        mAuth.signOut();
        if(user!=null) {
            boolean emailVerified = user.isEmailVerified();
            if (!emailVerified) {
                showToast("????????? ?????? ??? ????????? ??? ????????????");
            }
            finish();
        }
    }

    public void showToast(String message){
        Toast.makeText(SignUpActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}