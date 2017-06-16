package com.lupita.memorama;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FelicidadesActivity extends AppCompatActivity implements View.OnClickListener{

    private View view;
    private int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_felicidades);

        view=getWindow().getDecorView();
        view.setSystemUiVisibility(uiOptions);

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

    @Override
    protected void onResume() {
        super.onResume();
        view=getWindow().getDecorView();
        view.setSystemUiVisibility(uiOptions);
    }

}
