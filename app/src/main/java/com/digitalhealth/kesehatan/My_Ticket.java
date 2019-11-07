package com.digitalhealth.kesehatan;

import android.widget.ImageView;

public class My_Ticket {
    String jenis_kamar;
    String jumlah_pesanan;

    public My_Ticket() {
    }

    public My_Ticket(String jenis_kamar, String jumlah_pesanan, ImageView bpjs) {
        this.jenis_kamar = jenis_kamar;
        this.jumlah_pesanan = jumlah_pesanan;

    }

    public String getJenis_kamar() {
        return jenis_kamar;
    }

    public void setJenis_kamar(String jenis_kamar) {
        this.jenis_kamar = jenis_kamar;
    }

    public String getJumlah_pesanan() {
        return jumlah_pesanan;
    }

    public void setJumlah_pesanan(String jumlah_pesanan) {
        this.jumlah_pesanan = jumlah_pesanan;
    }

}
