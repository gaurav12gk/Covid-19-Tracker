package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
private ImageView covid19,covidtracker,doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        covid19=findViewById(R.id.covid19);
        covidtracker=findViewById(R.id.covidtracker);
        doctor=findViewById(R.id.doctor);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
covid19.animate().rotation(720).setDuration(1000);




        new Handler().postDelayed(new Runnable() {
            @Override public void run() {

                 Intent i = new Intent(SplashActivity    .this, MainActivity.class); startActivity(i);
                finish(); } }, 3000);
    }

}