package com.lupita.memorama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FelicidadesActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_felicidades);

        getSupportActionBar().hide();

        findViewById(R.id.felicidades_regresar).setOnClickListener(this);

        findViewById(R.id.feliciadaes_iv_jugar).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FelicidadesActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(FelicidadesActivity.this, MainActivity.class));
        finish();
    }
}
