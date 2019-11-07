package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class InfoRsAct extends AppCompatActivity {

    LinearLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_rs);

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembalidashboard = new Intent(InfoRsAct.this, Dashboard.class);
                startActivity(kembalidashboard);
                finish();
            }
        });
    }
}
