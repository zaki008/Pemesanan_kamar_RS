package com.digitalhealth.kesehatan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pemesanan1 extends AppCompatActivity {

    LinearLayout rs_cipto, rspi, rs_fatmawati, rs_medistra, rs_siloam, rs_prikasi;
    LinearLayout btn_back;
    TextView nama, user_balance;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan1);

        getUsernameLocal();

        rs_cipto = findViewById(R.id.rs_cipto);
        rspi = findViewById(R.id.rspi);
        rs_fatmawati = findViewById(R.id.rs_fatmawati);
        rs_medistra = findViewById(R.id.rs_medistra);
        rs_siloam = findViewById(R.id.rs_siloam);
        rs_prikasi = findViewById(R.id.rs_prikasi);
        btn_back = findViewById(R.id.btn_back);

        nama = findViewById(R.id.nama);
        user_balance = findViewById(R.id.user_balance);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama.setText(dataSnapshot.child("nama").getValue().toString());
                user_balance.setText("Rp"+" " +dataSnapshot.child("user_balance").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rs_cipto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujupemesanan2 = new Intent(Pemesanan1.this, Pemesanan2.class);
                menujupemesanan2.putExtra("jenis_kamar", "rs_cipto");
                startActivity(menujupemesanan2);
            }
        });

        rspi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujupemesanan2 = new Intent(Pemesanan1.this, Pemesanan2.class);
                menujupemesanan2.putExtra("jenis_kamar", "rspi");
                startActivity(menujupemesanan2);
            }
        });

        rs_fatmawati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujupemesanan2 = new Intent(Pemesanan1.this, Pemesanan2.class);
                menujupemesanan2.putExtra("jenis_kamar", "rs_fatmawati");
                startActivity(menujupemesanan2);
            }
        });

        rs_medistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujupemesanan2 = new Intent(Pemesanan1.this, Pemesanan2.class);
                menujupemesanan2.putExtra("jenis_kamar", "rs_medistra");
                startActivity(menujupemesanan2);
            }
        });

        rs_siloam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujupemesanan2 = new Intent(Pemesanan1.this, Pemesanan2.class);
                menujupemesanan2.putExtra("jenis_kamar", "rs_siloam");
                startActivity(menujupemesanan2);
            }
        });

        rs_prikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujupemesanan2 = new Intent(Pemesanan1.this, Pemesanan2.class);
                menujupemesanan2.putExtra("jenis_kamar", "rs_prikasi");
                startActivity(menujupemesanan2);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembalidashboard = new Intent(Pemesanan1.this, Dashboard.class);
                startActivity(kembalidashboard);
                finish();
            }
        });
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");
    }
}


