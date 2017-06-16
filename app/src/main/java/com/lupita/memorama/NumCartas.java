package com.lupita.memorama;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class NumCartas extends AppCompatActivity implements View.OnClickListener {

    private ImageView btn_4, btn_8, btn_12, regresar;
    private int modoJuego;
    private View view;
    private int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_cartas);

        view=getWindow().getDecorView();
        view.setSystemUiVisibility(uiOptions);

        Bundle bundle = getIntent().getExtras();
        modoJuego=bundle.getInt("modoJuego");

        regresar = (ImageView) findViewById(R.id.num_cartas_regresar);
        btn_4 = (ImageView) findViewById(R.id.btn_4);
        btn_8 = (ImageView) findViewById(R.id.btn_8);
        btn_12 = (ImageView) findViewById(R.id.btn_12);

        regresar.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_12.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this, JuegoActivity.class);
        intent.putExtra("modoJuego", modoJuego);
        switch (v.getId()) {
            case R.id.num_cartas_regresar:
                onBackPressed();
                break;
            case R.id.btn_4:
                intent.putExtra("numeroDeTarjetas", 4);
                break;

            case R.id.btn_8:
                intent.putExtra("numeroDeTarjetas", 8);
                break;

            case R.id.btn_12:
                intent.putExtra("numeroDeTarjetas", 12);
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
