package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessAct extends AppCompatActivity {

    Button explore_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        explore_now = findViewById(R.id.explore_now);

        explore_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujuSign = new Intent(SuccessAct.this, SignAct.class);
                startActivity(menujuSign);
                finish();
            }
        });
    }
}
