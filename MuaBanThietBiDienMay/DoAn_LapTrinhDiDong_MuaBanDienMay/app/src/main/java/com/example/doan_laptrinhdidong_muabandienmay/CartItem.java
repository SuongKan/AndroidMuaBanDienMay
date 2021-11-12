package com.example.doan_laptrinhdidong_muabandienmay;

import java.io.Serializable;

public class CartItem implements Serializable {
    SanPham sanPham;
    int soluong;

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public CartItem() {
    }

    public CartItem(SanPham sanPham, int soluong) {
        this.sanPham = sanPham;
        this.soluong = soluong;
    }
}
