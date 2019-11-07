package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digitalhealth.kesehatan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Detail_Pesanan extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_bayar, btnmines, btnplus;
    TextView textjumlahtiket, texttotalharga, textmybalance, jenis_kamar;
    Integer valueJumlahTiket = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    ImageView notice_kurang;
    Integer sisa_balance = 0;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "usernamekey";
    String  username_key = "";

    //generete nomor integer secara random
    //karena ingin membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        getUsernameLocal();

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_gol_baru = bundle.getString("jenis_gol");

        btnmines = findViewById(R.id.btnmines);
        btnplus = findViewById(R.id.btnplus);
        textjumlahtiket = findViewById(R.id.textjumlahtiket);
        btn_back = findViewById(R.id.btn_back);
        btn_bayar = findViewById(R.id.btn_bayar);
        texttotalharga = findViewById(R.id.texttotalharga);
        textmybalance = findViewById(R.id.textmylbalance);
        notice_kurang = findViewById(R.id.notice_kurang);
        jenis_kamar = findViewById(R.id.jenis_kamar);

        //setting value baru beberapa komponen
        textjumlahtiket.setText(valueJumlahTiket.toString());
        notice_kurang.setVisibility(View.GONE);
        //secara default hide minus
        btnmines.animate().alpha(0).setDuration(300).start();
        btnmines.setEnabled(false);

        //mengambil data dari users yang sedang login
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmybalance.setText("Rp"+ mybalance + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Rs").child(jenis_gol_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jenis_kamar.setText(dataSnapshot.child("jenis_kamar").getValue().toString());
                valuehargatiket = Integer.valueOf(dataSnapshot.child("harga_kamar").getValue().toString());

                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("Rp"+ valuetotalharga+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTiket+=1;
                textjumlahtiket.setText(valueJumlahTiket.toString());
                if(valueJumlahTiket>1){
                    btnmines.animate().alpha(1).setDuration(300).start();
                    btnmines.setEnabled(true);
                }
                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("Rp"+ valuetotalharga+"");
                if (valuetotalharga > mybalance){
                    btn_bayar.animate().translationY(250).alpha(0).setDuration(300).start();
                    btn_bayar.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_kurang.setVisibility(View.VISIBLE);
                }
            }
        });

        btnmines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTiket-=1;
                textjumlahtiket.setText(valueJumlahTiket.toString());
                if(valueJumlahTiket<2){
                    btnmines.animate().alpha(0).setDuration(300).start();
                    btnmines.setEnabled(false);
                }
                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("Rp"+ valuetotalharga+"");
                if (valuetotalharga <= mybalance){
                    btn_bayar.animate().translationY(0).alpha(1).setDuration(300).start();
                    btn_bayar.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_kurang.setVisibility(View.GONE);
                }
            }
        });
        //menyimpan data dari users yang sedang login dan membuat tabel baru yaitu view pesanan
        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key).child(jenis_kamar.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("jenis_kamar").setValue(jenis_kamar.getText().toString());
                        reference3.getRef().child("id_kamar").setValue(jenis_kamar.getText().toString()+nomor_transaksi);
                        reference3.getRef().child("jumlah_pesanan").setValue(valueJumlahTiket.toString());

                        Intent menujusuccespemesanan = new Intent(Detail_Pesanan.this, Success_pemesanan.class );
                        startActivity(menujusuccespemesanan);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //update data balance kepada users (yang sekarang login)
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = mybalance-valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onBackPressed();
            }
        });

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");
    }
}
