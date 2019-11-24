package com.example.myapplication.model;

public class User {

    private String username, nohp, about, birthday, gender, address;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
