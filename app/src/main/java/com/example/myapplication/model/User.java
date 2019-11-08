package com.example.myapplication.model;

public class User {

    private String username, nohp, about;

    public User() {
    }

    public User(String username, String nohp) {
        this.username = username;
        this.nohp = nohp;

    }

    public User(String username, String nohp, String about) {
        this.username = username;
        this.nohp = nohp;
        this.about = about;

    }

    public String getUsername() {
        return username;
    }

    public String getNohp() {
        return nohp;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
