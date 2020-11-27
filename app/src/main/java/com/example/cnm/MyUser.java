package com.example.cnm;

import java.util.Date;

public class MyUser {
    String user,name,pass,bday;
   // Date doB;
    boolean gender,trangthai;
    int img;

    public MyUser(String id, String name, String pass, String doB, boolean gender, int img) {
        this.user = id;
        this.name = name;
        this.pass = pass;
        this.bday = doB;
        this.gender = gender;
        this.img = img;
    }

    public MyUser() {
        this.user = "";
        this.name = "";
        this.pass = "";
        this.bday = null;
        this.gender = false;
        this.img = 0;
    }

    public boolean isStt() {
        return trangthai;
    }

    public void setStt(boolean stt) {
        this.trangthai = stt;
    }

    public String getId() {
        return user;
    }

    public void setId(String id) {
        this.user = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDoB() {
        return bday;
    }

    public void setDoB(String doB) {
        this.bday = doB;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
