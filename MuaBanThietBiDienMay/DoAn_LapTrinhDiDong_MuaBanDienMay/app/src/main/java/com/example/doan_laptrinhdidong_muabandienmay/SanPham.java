package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class SanPham implements Serializable {
    int sanphamID;
    String name;
    int soLuongTon;
    int giaTien;
    String moTa;
    String maLoai;
    String image;

    public SanPham() {

    }

    public SanPham(int sanphamID, String name, int soLuongTon, int giaTien, String moTa, String maLoai, String image) {
        this.sanphamID = sanphamID;
        this.name = name;
        this.soLuongTon = soLuongTon;
        this.giaTien = giaTien;
        this.moTa = moTa;
        this.maLoai = maLoai;
        this.image = image;
    }

    public int getSanphamID() {
        return sanphamID;
    }

    public void setSanphamID(int sanphamID) {
        this.sanphamID = sanphamID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Bitmap convertStringToBitMapFromAccess(Context context, String filename)
    {
        AssetManager assetManager=context.getAssets();
        try
        {
            InputStream is=assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        }
        catch (IOException e)
        {
            e.printStackTrace();;
        }
        return null;
    }
}
