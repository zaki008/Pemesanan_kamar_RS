package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation app_main, btt;
    ImageView app_logo;
    TextView app_title;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load animation
        app_main = AnimationUtils.loadAnimation(this, R.anim.app_main);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);
        //load element
        app_logo = findViewById(R.id.app_logo);
        app_title = findViewById(R.id.app_title);

        //run animation
        app_logo.startAnimation(app_main);
        app_title.startAnimation(btt);

        getUsernameLocal();

        Handler Timer = new Handler();
        Timer.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent menujugetstarted = new Intent(MainActivity.this, Get_Started.class);
                startActivity(menujugetstarted);
                finish();
            }
        }, 2000);
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");

        }
    }
