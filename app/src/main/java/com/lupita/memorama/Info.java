package com.lupita.memorama;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Info extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getSupportActionBar().hide();

        findViewById(R.id.info_regresar).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.info_regresar:
                onBackPressed();
                break;
        }
    }
}
