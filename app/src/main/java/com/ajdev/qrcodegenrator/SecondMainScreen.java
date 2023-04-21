package com.ajdev.qrcodegenrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main_screen);

        Button genratebtn = findViewById(R.id.genratebtn);
        Button scanebtn = findViewById(R.id.scanebtn);

        genratebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent genratebtnintent = new Intent(SecondMainScreen.this,genrateactivity.class);
                startActivity(genratebtnintent);
            }
        });
        scanebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent infobtnintent = new Intent(SecondMainScreen.this,infoactivity.class);
                startActivity(infobtnintent);
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}