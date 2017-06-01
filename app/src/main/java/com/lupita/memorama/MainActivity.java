package com.lupita.memorama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  ImageButton info, ib_mx, ib_uni;
    private Button mx_text,uni_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info= (ImageButton) findViewById(R.id.info);
        ib_mx= (ImageButton) findViewById(R.id.ib_mx);
        ib_uni= (ImageButton) findViewById(R.id.ib_uni);
        mx_text= (Button) findViewById(R.id.mx_text);
        uni_text= (Button) findViewById(R.id.uni_text);

        info.setOnClickListener(this);
        ib_mx.setOnClickListener(this);
        ib_uni.setOnClickListener(this);
        mx_text.setOnClickListener(this);
        uni_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this, NumCartas.class);
        switch (v.getId()) {
            case R.id.info:
                Intent intent1=new Intent(this, Info.class);
                startActivity(intent1);
                break;

            case R.id.ib_mx:
                intent.putExtra("modoJuego", 0);
                startActivity(intent);
                break;

            case R.id.mx_text:
                intent.putExtra("modoJuego", 0);
                startActivity(intent);
                break;

            case R.id.ib_uni:
                intent.putExtra("modoJuego", 1);
                startActivity(intent);
                break;

            case R.id.uni_text:
                intent.putExtra("modoJuego", 1);
                startActivity(intent);
                break;
        }
    }
}