package com.lupita.memorama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lupita.memorama.Clases.Tarjeta;

public class InformacionTarjetaActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean gane;
    private ImageView iv_atras;
    private TextView tv_atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_tarjeta);

        getSupportActionBar().hide();

        iv_atras= (ImageView) findViewById(R.id.iv_atras);
        tv_atras= (TextView) findViewById(R.id.tv_atras);

        iv_atras.setOnClickListener(this);
        tv_atras.setOnClickListener(this);

        Tarjeta tarjeta = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tarjeta = (Tarjeta) bundle.get("tarjeta");
            gane = bundle.getBoolean("gane");
        }

        ImageView imagen = (ImageView) findViewById(R.id.info_imagen);
        TextView texto = (TextView) findViewById(R.id.info_informacion);

        if (tarjeta != null){
            Glide.with(this).load(tarjeta.getIdDrawable()).into(imagen);
            texto.setText(tarjeta.getInformation());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_atras:
                onBackPressed();
                break;

            case R.id.tv_atras:
                onBackPressed();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (gane) {
            Intent intent = new Intent(this, FelicidadesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else
            super.onBackPressed();
    }


}
