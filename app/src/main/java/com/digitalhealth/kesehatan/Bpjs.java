package com.digitalhealth.kesehatan;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class Bpjs extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_pesan, btn_plus_photo;
    ImageView file_uploads;
    TextView jenis_kamar, textjumlahtiket ;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference, reference2;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    //generete nomor integer secara random
    //karena ingin membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpjs);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        final String jenis_gol_baru = bundle.getString("jenis_gol");

        btn_pesan = findViewById(R.id.btn_pesan);
        btn_back = findViewById(R.id.btn_back);
        btn_plus_photo = findViewById(R.id.btn_plus_photo);
        file_uploads = findViewById(R.id.file_uploads);
        jenis_kamar = findViewById(R.id.jenis_kamar);
        textjumlahtiket = findViewById(R.id.textjumlahtiket);

        btn_plus_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });


        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Rs").child(jenis_gol_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jenis_kamar.setText(dataSnapshot.child("jenis_kamar").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_pesan.setEnabled(true);
                btn_pesan.setText("tunggu ya gan");

                //menyimpan data kepada firebase
                reference2 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key).child(jenis_kamar.getText().toString() + nomor_transaksi);
                storage = FirebaseStorage.getInstance().getReference().child("uploads_photo").child(username_key).child(jenis_kamar.getText().toString()+nomor_transaksi);

                //validasi untuk file
                if (photo_location != null){
                    StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                    storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String uri_photo = taskSnapshot.getStorage().getDownloadUrl().toString();
                            reference2.getRef().child("url_photo").setValue(uri_photo);
                            reference2.getRef().child("jenis_kamar").setValue(jenis_kamar.getText().toString());
                            reference2.getRef().child("id_kamar").setValue(jenis_kamar.getText().toString()+nomor_transaksi);
                            reference2.getRef().child("jumlah_pesanan").setValue(textjumlahtiket.getText().toString());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent menujusuccespemesanan = new Intent(Bpjs.this, Success_pemesanan.class);
                            startActivity(menujusuccespemesanan);
                        }
                    });
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

    String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
            ;
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(file_uploads);
        }
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key = sharedPreferences.getString(username_key, "");
    }
}
