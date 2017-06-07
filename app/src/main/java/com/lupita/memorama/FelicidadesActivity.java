package com.lupita.memorama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FelicidadesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_felicidades);

        getSupportActionBar().hide();

        Button btn = (Button) findViewById(R.id.feliciadaes_btn_jugar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FelicidadesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
