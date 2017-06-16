package com.lupita.memorama;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  ImageView iv_info, iv_mx, iv_uni;
    private View view;
    private int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view=getWindow().getDecorView();
        view.setSystemUiVisibility(uiOptions);

        iv_info= (ImageView) findViewById(R.id.iv_info);
        iv_mx= (ImageView) findViewById(R.id.iv_mx);
        iv_uni= (ImageView) findViewById(R.id.iv_uni);

        iv_info.setOnClickListener(this);
        iv_mx.setOnClickListener(this);
        iv_uni.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this, NumCartas.class);
        switch (v.getId()) {
            case R.id.iv_info:
                intent =new Intent(this, Info.class);
                break;

            case R.id.iv_mx:
                intent.putExtra("modoJuego", 0);
                break;

            case R.id.iv_uni:
                intent.putExtra("modoJuego", 1);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        view=getWindow().getDecorView();
        view.setSystemUiVisibility(uiOptions);
    }

}