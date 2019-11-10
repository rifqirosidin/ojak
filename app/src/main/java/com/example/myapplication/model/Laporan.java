package com.example.myapplication.model;

public class Laporan {

    String name, bulan, deskripsi, kategori, urlImage;

    public Laporan(String name, String bulan, String deskripsi, String kategori, String urlImage) {
        this.name = name;
        this.bulan = bulan;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.urlImage = urlImage;
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

    public String getUrlImage() {
        return urlImage;
    }
}
