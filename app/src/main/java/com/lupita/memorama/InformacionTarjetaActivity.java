package com.lupita.memorama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lupita.memorama.Clases.Tarjeta;

public class InformacionTarjetaActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean gane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_tarjeta);

        getSupportActionBar().hide();

        findViewById(R.id.info_iv_regresar).setOnClickListener(this);

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


}
