package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Detail_Gol extends AppCompatActivity {

    LinearLayout btn_back;
    TextView title_kamar, ketentuan_kamar, ketentuan_wifi, ketentuan_cctv;
    Button btn_pesan;
    ImageView gambar_kamar;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gol);

        btn_back = findViewById(R.id.btn_back);
        btn_pesan = findViewById(R.id.btn_pesan);

        title_kamar = findViewById(R.id.title_kamar);
        ketentuan_kamar = findViewById(R.id.ketentuan_kamar);
        ketentuan_wifi = findViewById(R.id.ketentuan_wifi);
        ketentuan_cctv = findViewById(R.id.ketentuan_cctv);
        gambar_kamar = findViewById(R.id.gambar_kamar);


        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_gol_baru = bundle.getString("jenis_gol");

        Toast.makeText(getApplicationContext(), "Welcome to " + " " + jenis_gol_baru, Toast.LENGTH_SHORT).show();


        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Rs").child(jenis_gol_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                title_kamar.setText(dataSnapshot.child("jenis_kamar").getValue().toString());
                ketentuan_kamar.setText(dataSnapshot.child("jumlah_kasur").getValue().toString());
                ketentuan_wifi.setText(dataSnapshot.child("kecepatan_wifi").getValue().toString());
                ketentuan_cctv.setText(dataSnapshot.child("jumlah_CCTV").getValue().toString());
                Picasso.with(Detail_Gol.this)
                        .load(dataSnapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(gambar_kamar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujudetailpesanan = new Intent(Detail_Gol.this, Pilih_Pembayaran.class);
                menujudetailpesanan.putExtra("jenis_gol",jenis_gol_baru);
                startActivity(menujudetailpesanan);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
