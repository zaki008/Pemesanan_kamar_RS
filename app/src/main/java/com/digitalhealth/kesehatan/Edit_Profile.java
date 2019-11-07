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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Profile extends AppCompatActivity {

    LinearLayout btn_back;
    Button save_profile;
    EditText xnama,xusername, xpassword, xemail_address ;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);

        btn_back = findViewById(R.id.btn_back);
        save_profile = findViewById(R.id.save_profile);

        xnama = findViewById(R.id.xnama);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        xemail_address = findViewById(R.id.xemail_address);

        getUsernameLocal();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama.setText(dataSnapshot.child("nama").getValue().toString());
                xusername.setText(dataSnapshot.child("username").getValue().toString());
                xpassword.setText(dataSnapshot.child("password").getValue().toString());
                xemail_address.setText(dataSnapshot.child("email_address").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save_profile.setEnabled(false);
                save_profile.setText("sabar ya gan");

                String nama_pengguna = xusername.getText().toString();
                String name = xnama.getText().toString();
                String kata_sandi = xpassword.getText().toString();
                String alamat_email = xemail_address.getText().toString();

                if (nama_pengguna.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "hayo, lupa ya isi username", Toast.LENGTH_SHORT).show();
                    save_profile.setEnabled(true);
                    save_profile.setText("SAVE PROFILE");
                } else if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "hayo, lupa ya isi nama", Toast.LENGTH_SHORT).show();
                    save_profile.setEnabled(true);
                    save_profile.setText("SAVE PROFILE");
                } else if (kata_sandi.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "hayo, lupa ya isi password", Toast.LENGTH_SHORT).show();
                    save_profile.setEnabled(true);
                    save_profile.setText("SAVE PROFILE");
                } else if (alamat_email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "hayo, lupa ya isi Email Address", Toast.LENGTH_SHORT).show();
                    save_profile.setEnabled(true);
                    save_profile.setText("SAVE PROFILE");
                } else {

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child("nama").setValue(xnama.getText().toString());
                            dataSnapshot.getRef().child("username").setValue(xusername.getText().toString());
                            dataSnapshot.getRef().child("password").setValue(xpassword.getText().toString());
                            dataSnapshot.getRef().child("email_address").setValue(xemail_address.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Intent kembaliViewpemesanan = new Intent(Edit_Profile.this, View_Pesanan.class);
                    startActivity(kembaliViewpemesanan);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onBackPressed();
            }
        });
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");
    }
}
