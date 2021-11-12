package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class ChiTietHoaDon implements Serializable {
    int MaHD,MaCTHD,MaSP;
    String TenSP,LoaiSP,HinhAnh;
    int SoLuong,DonGia;

    public ChiTietHoaDon() {
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public int getMaCTHD() {
        return MaCTHD;
    }

    public void setMaCTHD(int maCTHD) {
        MaCTHD = maCTHD;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getLoaiSP() {
        return LoaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        LoaiSP = loaiSP;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getDonGia() {
        return DonGia;
    }

    public void setDonGia(int donGia) {
        DonGia = donGia;
    }

    public int getTongTien()
    {
        return this.DonGia*this.SoLuong;
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
