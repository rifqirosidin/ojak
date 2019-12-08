package com.example.myapplication.model;

public class WarningReport {
    String type, email, lokasi, latitude, longitude;

    public WarningReport(String type, String name, String latitude, String longitude) {
        this.type = type;
        this.email = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getLokasi() {
        return lokasi;
    }
    public void setLokasi(String lokasi){
        this.lokasi = lokasi;
    }

    public String getLatitude() {
        return latitude;
    }


    public String getLongitude() {
        return longitude;
    }

}
