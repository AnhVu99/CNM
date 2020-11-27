package Models;

import java.util.Date;

public class MyUser {
    String id,name,pass;
    Date doB;
    boolean gender;
    int img;

    public MyUser(String id, String name, String pass, Date doB, boolean gender, int img) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.doB = doB;
        this.gender = gender;
        this.img = img;
    }

    public MyUser() {
        this.id = "";
        this.name = "";
        this.pass = "";
        this.doB = null;
        this.gender = false;
        this.img = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDoB() {
        return doB;
    }

    public void setDoB(Date doB) {
        this.doB = doB;
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
