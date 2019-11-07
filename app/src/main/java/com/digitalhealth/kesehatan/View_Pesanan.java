package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class View_Pesanan extends AppCompatActivity {
    Button dashboard;
    Button edit_profil, sign_out;

    TextView nama;

    DatabaseReference reference, reference2;
    String USERNAME_KEY = "usernamekey";
    String  username_key = "";
    RecyclerView my_pesananku;

    ArrayList<My_Ticket> list;
    KamarAdapter kamarAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pesanan);

        getUsernameLocal();
        dashboard = findViewById(R.id.dashboard);
        edit_profil = findViewById(R.id.edit_profil);
        sign_out = findViewById(R.id.sign_out);

        nama = findViewById(R.id.nama);
        my_pesananku = findViewById(R.id.mypesananku_place);
        my_pesananku.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<My_Ticket>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama.setText(dataSnapshot.child("nama").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembalidashobard = new Intent(View_Pesanan.this, Dashboard.class);
                startActivity(kembalidashobard);
                finish();
            }
        });



        edit_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujueditprofile = new Intent(View_Pesanan.this, Edit_Profile.class);
                startActivity(menujueditprofile);
                finish();
            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    My_Ticket p  = dataSnapshot1.getValue(My_Ticket.class);
                    list.add(p);
                }

                kamarAdapter = new KamarAdapter(View_Pesanan.this, list);
                my_pesananku.setAdapter(kamarAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menghapus value untuk keluar dari username local

                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();

                Intent keluarusermanelocal = new Intent(View_Pesanan.this, SignAct.class);
                startActivity(keluarusermanelocal);
                finish();
            }
        });
    }
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");
    }
}
