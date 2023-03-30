package com.ajdev.qrcodegenrator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class infoactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoactivity);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}