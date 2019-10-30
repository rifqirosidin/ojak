package com.example.myapplication.model;

public class User {

    private String username, nohp;

    public User() {
    }

    public User(String username, String nohp) {
        this.username = username;
        this.nohp = nohp;
    }

    public String getUsername() {
        return username;
    }

    public String getNohp() {
        return nohp;
    }
}
