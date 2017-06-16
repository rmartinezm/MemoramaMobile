package com.lupita.memorama;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lupita.memorama.Clases.Tarjeta;

public class InformacionTarjetaActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean gane;
    private View view;
    private int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_tarjeta);

        view=getWindow().getDecorView();
        view.setSystemUiVisibility(uiOptions);

        findViewById(R.id.info_iv_regresar).setOnClickListener(this);

        Tarjeta tarjeta = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tarjeta = (Tarjeta) bundle.get("tarjeta");
            gane = bundle.getBoolean("gane");
        }

        ImageView imagen = (ImageView) findViewById(R.id.info_imagen);
        ImageView ivTexto = (ImageView) findViewById(R.id.info_iv_caja_texto);

        if (tarjeta != null){
            Glide.with(this).load(tarjeta.getIdCompleta()).into(imagen);
            Glide.with(this).load(tarjeta.getIdInformation()).into(ivTexto);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_iv_regresar:
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

    @Override
    protected void onResume() {
        super.onResume();
        view=getWindow().getDecorView();
        view.setSystemUiVisibility(uiOptions);
    }


}
