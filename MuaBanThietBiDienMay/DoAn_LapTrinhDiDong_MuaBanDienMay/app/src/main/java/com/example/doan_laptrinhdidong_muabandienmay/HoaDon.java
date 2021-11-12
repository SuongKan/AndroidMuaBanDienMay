package com.example.doan_laptrinhdidong_muabandienmay;

import java.io.Serializable;
import java.util.Date;

public class HoaDon implements Serializable {
    private int MaHD;
    private Date NgayLap;
    private int MaTK;
    private String DiaChi;
    private int TongTien;

    public HoaDon() {
    }

    public HoaDon(int maHD, Date ngayLap, int maTK, String diaChi, int tongTien) {
        this.MaHD = maHD;
        this.NgayLap = ngayLap;
        this.MaTK = maTK;
        this.DiaChi = diaChi;
        this.TongTien = tongTien;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public Date getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(Date ngayLap) {
        NgayLap = ngayLap;
    }

    public int getMaTK() {
        return MaTK;
    }

    public void setMaTK(int maTK) {
        MaTK = maTK;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public int getTongTien() {
        return TongTien;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }
}
