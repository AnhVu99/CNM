package com.example.cnm;

public class itemDB {
    int anh,stt;
    String SDT,Ten;

    public itemDB(int anh, int stt, String SDT, String ten) {
        this.anh = anh;
        this.stt = stt;
        this.SDT = SDT;
        Ten = ten;
    }

    public int getAnh() {
        return anh;
    }

    public void setAnh(int anh) {
        this.anh = anh;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
