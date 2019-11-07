package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Get_Started extends AppCompatActivity {

    Button btn_sign;
    Button new_account;
    Animation ttb,btt;
    TextView intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__started);

        btn_sign = findViewById(R.id.btn_sign);
        new_account = findViewById(R.id.new_account);
        intro = findViewById(R.id.intro);

        //load animation
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //run animation
            intro.startAnimation(ttb);
            btn_sign.startAnimation(btt);
            new_account.startAnimation(btt);

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujusign = new Intent(Get_Started.this, SignAct.class);
                startActivity(menujusign);
                finish();

            }
        });

        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujuRegister = new Intent(Get_Started.this, RegisterAct.class);
                startActivity(menujuRegister);
                finish();
            }
        });

    }
}
