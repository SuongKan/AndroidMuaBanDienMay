package com.example.doan_laptrinhdidong_muabandienmay;

import java.io.Serializable;

public class User implements Serializable {
    private int makh;
    private String username;
    private String pass;
    private String email;
    private String diachi;
    private String gioitinh;
    public User(){}
    public User(int makh,String username,String pass,String email,String diachi,String gioitinh){
        this.makh=makh;
        this.username=username;
        this.pass=pass;
        this.email=email;
        this.diachi=diachi;
        this.gioitinh=gioitinh;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }
}
