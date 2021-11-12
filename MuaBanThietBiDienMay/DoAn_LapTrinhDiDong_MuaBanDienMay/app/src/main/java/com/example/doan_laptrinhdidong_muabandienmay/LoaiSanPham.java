package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoaiSanPham {
    String loaiSanPhamID;
    String name;
    String image;
    ArrayList<SanPham> arrayList;

    public LoaiSanPham(String loaiSanPhamID, String name, String image, ArrayList<SanPham> arrayList) {
        this.loaiSanPhamID = loaiSanPhamID;
        this.name = name;
        this.image = image;
        this.arrayList = arrayList;
    }

    public LoaiSanPham(String loaiSanPhamID, String name, String image) {
        this.loaiSanPhamID = loaiSanPhamID;
        this.name = name;
        this.image = image;
    }

    public LoaiSanPham() {
    }

    public String getLoaiSanPhamID() {
        return loaiSanPhamID;
    }

    public void setLoaiSanPhamID(String loaiSanPhamID) {
        this.loaiSanPhamID = loaiSanPhamID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<SanPham> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<SanPham> arrayList) {
        this.arrayList = arrayList;
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
