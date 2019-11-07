package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pemesanan2 extends AppCompatActivity {

    LinearLayout vip, gol_1, gol_2, gol_3;
    LinearLayout btn_back;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan2);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_kamar_baru = bundle.getString("jenis_kamar");

        Toast.makeText(getApplicationContext(), "Welcome    to " + " " + jenis_kamar_baru, Toast.LENGTH_SHORT).show();

        reference = FirebaseDatabase.getInstance().getReference().child("Rs").child(jenis_kamar_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        vip = findViewById(R.id.vip);
        gol_1 = findViewById(R.id.gol_1);
        gol_2 = findViewById(R.id.gol_2);
        gol_3 = findViewById(R.id.gol_3);

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
            }
        });

        vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujudetailgol = new Intent(Pemesanan2.this, Detail_Gol.class);
                //meletakkan data pada intent
                menujudetailgol.putExtra("jenis_gol","VIP");
                startActivity(menujudetailgol);
            }
        });

        gol_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujudetailgol = new Intent(Pemesanan2.this, Detail_Gol.class);
                menujudetailgol.putExtra("jenis_gol", "GOL1");
                startActivity(menujudetailgol);
            }
        });

        gol_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujudetailgol = new Intent(Pemesanan2.this, Detail_Gol.class);
                menujudetailgol.putExtra("jenis_gol", "GOL2");
                startActivity(menujudetailgol);
            }
        });

        gol_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujudetailgol = new Intent(Pemesanan2.this, Detail_Gol.class);
                menujudetailgol.putExtra("jenis_gol", "GOL3");
                startActivity(menujudetailgol);
            }
        });
    }
}

