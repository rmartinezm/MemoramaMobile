package com.lupita.memorama;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class NumCartas extends AppCompatActivity implements View.OnClickListener {

    private ImageView btn_4, btn_8, btn_12, regresar;
    private int modoJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_cartas);

        getSupportActionBar().hide();

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
}
