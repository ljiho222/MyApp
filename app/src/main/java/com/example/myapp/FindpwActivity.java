package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class FindpwActivity extends AppCompatActivity {
    // Firebase
    private FirebaseAuth mAuth;

    // widgets
    private EditText editEmail;
    private Button btnSend;
    private TextView textMessage;

    private TextInputLayout input_id_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);

        initFirebase();
        initView();
    }

    public void initFirebase(){
        mAuth=FirebaseAuth.getInstance();
    }

    public void initView(){
        editEmail=(EditText)findViewById(R.id.editEmail);
        btnSend=(Button)findViewById(R.id.btnSend);
        textMessage=(TextView)findViewById(R.id.textMessage);

        input_id_layout=(TextInputLayout)findViewById(R.id.input_id_layout);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=input_id_layout.getEditText().getText().toString().trim();
                if(email.equals("")){
                    textMessage.setText("이메일을 입력하세요");
                }
                else{
                    sendEmail(email);
                }
            }
        });
    }

    public void sendEmail(String email){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    showToast("비밀번호 재설정 메일을 보냈습니다");
                    updateUI();
                }
                else{
                    showToast("이메일이 올바른지 확인하세요");
                }
            }
        });
    }

    public void showToast(String message){
        Toast.makeText(FindpwActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    public void updateUI(){
        finish();
    }
}