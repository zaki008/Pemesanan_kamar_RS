package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;

public class Pilih_Pembayaran extends AppCompatActivity {

    LinearLayout saldo,bpjs;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_pembayaran);

        saldo = findViewById(R.id.saldo);
        bpjs = findViewById(R.id.bpjs);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_gol_baru = bundle.getString("jenis_gol");

        saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujudetailpesanan = new Intent(Pilih_Pembayaran.this, Detail_Pesanan.class);
                menujudetailpesanan.putExtra("jenis_gol",jenis_gol_baru);
                startActivity(menujudetailpesanan);
            }
        });

        bpjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujudetailbpjs = new Intent(Pilih_Pembayaran.this, Bpjs.class);
                menujudetailbpjs.putExtra("jenis_gol",jenis_gol_baru);
                startActivity(menujudetailbpjs);
            }
        });
    }
}
