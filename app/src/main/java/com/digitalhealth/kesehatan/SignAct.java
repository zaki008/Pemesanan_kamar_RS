package com.digitalhealth.kesehatan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignAct extends AppCompatActivity {

    TextView create_new_account;
    Button btn_sign;
    EditText xusername, xpassword;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String  username_key = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        create_new_account = findViewById(R.id.create_new_account);
        btn_sign = findViewById(R.id.btn_sign);

        create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menujuRegister = new Intent(SignAct.this, RegisterAct.class);
                startActivity(menujuRegister);
            }
        });

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_sign.setEnabled(false);
                btn_sign.setText("sabar ya gan");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "hayo, lupa ya isi username", Toast.LENGTH_SHORT).show();
                    btn_sign.setEnabled(true);
                    btn_sign.setText("SIGN IN");
                }
                else {
                    if (password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "hayo,lupa ya isi password", Toast.LENGTH_SHORT).show();
                        btn_sign.setEnabled(true);
                        btn_sign.setText("SIGN IN");
                    }
                    else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    //ambil data password
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    //validasi password dengan yang ada di database

                                    if (password.equals(passwordFromFirebase)){

                                        //menyimpan data kepada local storage
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        //pindah ke dashboard
                                        Intent menujudashboard = new Intent(SignAct.this, Dashboard.class);
                                        startActivity(menujudashboard);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"password tidak tersedia (coba lagi gan)",Toast.LENGTH_SHORT).show();
                                        btn_sign.setEnabled(true);
                                        btn_sign.setText("SIGN IN");
                                    }

                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"username tidak tersedia (coba lagi gan )",Toast.LENGTH_SHORT).show();
                                    btn_sign.setEnabled(true);
                                    btn_sign.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });
    }
}
