package com.example.myapplication.model;

public class Laporan {

    String name, bulan, deskripsi, kategori;

    public Laporan(String name, String bulan, String deskripsi, String kategori) {
        this.name = name;
        this.bulan = bulan;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
    }

    public String getBulan() {
        return bulan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getKategori() {
        return kategori;
    }

    public String getName() {
        return name;
    }
}
