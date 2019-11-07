package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Success_pemesanan extends AppCompatActivity {

    Button btn_lihat_pesananku;
    Button dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_pemesanan);

        btn_lihat_pesananku = findViewById(R.id.btn_lihat_pesananku);
        dashboard = findViewById(R.id.dashboard);

        btn_lihat_pesananku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujulihatpesanan = new Intent(Success_pemesanan.this, View_Pesanan.class);
                startActivity(menujulihatpesanan);
                finish();
            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembalidashboard = new Intent(Success_pemesanan.this, Dashboard.class);
                startActivity(kembalidashboard);
                finish();
            }
        });
    }
}
