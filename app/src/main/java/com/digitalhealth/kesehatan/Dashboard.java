package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {

    Button view_pesanan;
    Button info_rs;
    Button pemesanan_kamar;
    TextView nama, user_balance;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getUsernameLocal();

        view_pesanan = findViewById(R.id.view_pesanan);
        info_rs = findViewById(R.id.info_rs);
        pemesanan_kamar = findViewById(R.id.pemesanan_kamar);

        nama = findViewById(R.id.nama);
        user_balance = findViewById(R.id.user_balance);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama.setText(dataSnapshot.child("nama").getValue().toString());
                user_balance.setText("Rp"+ " " + dataSnapshot.child("user_balance").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        view_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujuviewpesanan = new Intent(Dashboard.this, View_Pesanan.class);
                startActivity(menujuviewpesanan);
                finish();
            }
        });

        info_rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujuInfors = new Intent(Dashboard.this, InfoRsAct.class);
                startActivity(menujuInfors);
                finish();
            }
        });

        pemesanan_kamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujupemesanan = new Intent(Dashboard.this, Pemesanan1.class);
                startActivity(menujupemesanan);
                finish();
            }
        });
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");
    }
}
