package com.lupita.memorama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Info extends AppCompatActivity {

    ImageView logo_unam,logo_umobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        logo_unam= (ImageView) findViewById(R.id.logo_unam);
        logo_umobile= (ImageView) findViewById(R.id.logo_umobile);

        Glide.with(this).load(R.drawable.cpp_logo).into(logo_unam);
        Glide.with(this).load(R.drawable.csharp_logo).into(logo_umobile);
    }
}
