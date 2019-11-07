package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterAct extends AppCompatActivity {

        LinearLayout btn_back;
        Button btn_continue;
        EditText nama,username, password, email_address, telpon ;

        DatabaseReference reference, reference_username_kunci;

        String USERNAME_KEY = "usernamekey";
        String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getUsernameLocal();

        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);


        btn_back = findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembaliGetstartted = new Intent(RegisterAct.this, Get_Started.class);
                startActivity(kembaliGetstartted);
                finish();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_continue.setEnabled(false);
                btn_continue.setText("Tunggu ya gan");

                String nama_pengguna = username.getText().toString();
                String name = nama.getText().toString();
                String kata_sandi = password.getText().toString();
                String alamat_email = email_address.getText().toString();

                if (nama_pengguna.isEmpty()){
                    Toast.makeText(getApplicationContext(), "hayo, lupa ya isi username", Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("CONTINUE");
                }
                else if (name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "hayo, lupa ya isi nama", Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("CONTINUE");
                }
                else if (kata_sandi.isEmpty()){
                    Toast.makeText(getApplicationContext(), "hayo, lupa ya isi password", Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("CONTINUE");
                }
                else if (alamat_email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "hayo, lupa isi email_address", Toast.LENGTH_SHORT).show();
                    btn_continue.setEnabled(true);
                    btn_continue.setText("CONTINUE");
                }
                else {
                    reference_username_kunci = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                    reference_username_kunci.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(getApplicationContext(),"username sudah tersedia!! coba lagi ya",Toast.LENGTH_SHORT).show();

                                btn_continue.setEnabled(true);
                                btn_continue.setText("CONTINUE");
                            }
                            else {
                                //menyimpan data kepada local storage
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, username.getText().toString());
                                editor.apply();
                                //simpan kedatabase
                                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                        dataSnapshot.getRef().child("nama").setValue(nama.getText().toString());
                                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                        dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                                        dataSnapshot.getRef().child("user_balance").setValue("10000000");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                Intent menujusuccesAct = new Intent(RegisterAct.this, SuccessAct.class);
                                startActivity(menujusuccesAct);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");
    }
}
