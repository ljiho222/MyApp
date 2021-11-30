package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class Pet_Card extends AppCompatActivity implements View.OnClickListener{
    private ImageView iv_pet;
    private TextView tv_happenPlace, tv_kindCd, tv_processState, tv_sexCd, tv_noticeSdt, tv_specialMark;
    private CardView view_card;
    private String tvpet, tvhappenPlace, tvkindCd, tvprocessState, tvsexCd, tvnoticeSdt, tvspecialMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.CENTER);

        setContentView(R.layout.activity_pet_card);

        //String happenPlace,kindCd,sexCd,popfile;
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 1.0); //Display 사이즈의 70%
        //int height = (int) (display.getHeight() * 0.2);  //Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContent();

        Intent intent = getIntent();
        //tvhappenPlace, tvkindCd, tvprocessState, tvsexCd, tvnoticeSdt, tvspecialMark
        tvhappenPlace = intent.getStringExtra("happenPlace");
        tvkindCd = intent.getStringExtra("kindCd");
        tvprocessState = intent.getStringExtra("processState");
        tvnoticeSdt = intent.getStringExtra("noticeSdt");
        tvsexCd = intent.getStringExtra("sexCd");
        tvspecialMark = intent.getStringExtra("specialMark");
        tvpet = intent.getStringExtra("popfile");

        tv_happenPlace.setText(tvhappenPlace);
        tv_kindCd.setText(tvkindCd);
        tv_processState.setText(tvprocessState);
        tv_noticeSdt.setText(tvnoticeSdt);
        tv_sexCd.setText(tvsexCd);
        tv_specialMark.setText(tvspecialMark);
        iv_pet.setImageDrawable(Drawable.createFromPath(tvpet));
    }
    @Override
    public void onBackPressed() {
        //Toast.makeText(ListDialogActivity.this, "세부정보로 넘어가게 구현", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private void setContent(){
        //tvhappenPlace, tvkindCd, tvprocessState, tvsexCd, tvnoticeSdt, tvspecialMark
        tv_happenPlace = (TextView) findViewById(R.id.tv_happenPlace);
        tv_kindCd = (TextView) findViewById(R.id.tv_kindCd);
        tv_processState =(TextView) findViewById(R.id.tv_processState);
        tv_sexCd = (TextView) findViewById(R.id.tv_sexCd);
        tv_noticeSdt = (TextView) findViewById(R.id.tv_noticeSdt);
        tv_specialMark = (TextView) findViewById(R.id.tv_specialMark);
        iv_pet = (ImageView) findViewById(R.id.iv_pet);
        //btn_route.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*
            case R.id.view_cardforlist:
                //Toast.makeText(ListDialogActivity.this, "세부정보로 넘어가게 구현", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_call:
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(callnum)));
                //Toast.makeText(MarkerDialogActivity.this, "전화로 넘어가게 구현", Toast.LENGTH_SHORT).show();
                break;
            */
            default:
                break;
        }
    }
}