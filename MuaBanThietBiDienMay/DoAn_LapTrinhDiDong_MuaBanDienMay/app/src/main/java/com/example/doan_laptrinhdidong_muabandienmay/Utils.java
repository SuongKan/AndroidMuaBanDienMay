package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Utils implements Serializable {
    SharedPreferences sharedpreferences;
    Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Utils() {
    }

    public Utils(Context context) {
        this.context = context;
        this.sharedpreferences = context.getSharedPreferences(LoginActivity.UserPrefer, Context.MODE_PRIVATE);
    }

    static  String filename="database.txt";

    static ArrayList<SanPham> furnitureHistory=new ArrayList<>();
    static ArrayList<CartItem> shoppingCart=new ArrayList<>();
    static ArrayList<SanPham> favoritesList=new ArrayList<>();

    public void addHistory(SanPham sanPham)
    {
        this.furnitureHistory.add(0,sanPham);


    }
    public void saveHistoryList()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        try {
            editor.putString("HistoryList", ObjectSerializer.serialize(this.furnitureHistory));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public void addShoppingCart(CartItem cartItem)
    {
        CartItem tmp=timItem(cartItem);
        if(tmp!=null)
        {
            shoppingCart.get(shoppingCart.indexOf(tmp)).setSoluong(shoppingCart.get(shoppingCart.indexOf(tmp)).getSoluong()+cartItem.getSoluong());
        }
        else
        {
            shoppingCart.add(cartItem);
        }
    }
    public void saveShoppingCart()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        try {
            editor.putString("CartList", ObjectSerializer.serialize(shoppingCart));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public void removeShoppingCart(CartItem cartItem)
    {
        for (CartItem i:this.shoppingCart) {
            if(i.sanPham.getSanphamID()==cartItem.sanPham.getSanphamID())
            {
                this.shoppingCart.remove(i);
                return;
            }
        }
    }

    private CartItem timItem(CartItem item)
    {
        for (CartItem i:this.shoppingCart) {
            if(i.getSanPham().getSanphamID()==item.getSanPham().getSanphamID())
            {
                return i;
            }
        }
        return null;
    }

    public int TinhTongTien()
    {
        int tongtien=0;
        for (CartItem cartItem:this.shoppingCart) {
            tongtien=tongtien+(cartItem.getSanPham().getGiaTien()*cartItem.getSoluong());
        }
        return tongtien;
    }

    public void addFavoritesList(SanPham sanPham)
    {
        this.favoritesList.add(sanPham);
    }

    public void removeFavoritesList(SanPham sanPham)
    {
        for (SanPham i:this.favoritesList) {
            if(i.getSanphamID()==sanPham.getSanphamID())
            {
                this.favoritesList.remove(i);
                return;
            }
        }

    }

    public void saveFavoriesList()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        try {
            editor.putString("FavoriesList", ObjectSerializer.serialize(this.favoritesList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public boolean isFavorites(SanPham item)
    {
        boolean kq=false;
        for (SanPham i:this.favoritesList) {
            if(i.getSanphamID()==item.getSanphamID())
            {
                kq=true;
            }
        }
        return kq;
    }

    public ArrayList<SanPham> getFurnitureHistory(){ return this.furnitureHistory; }
    public void setFurnitureHistory(ArrayList<SanPham> sanPhams){ this.furnitureHistory=sanPhams; }

    public ArrayList<CartItem>getShoppingCart(){return this.shoppingCart;}

    public void setShoppingCart(ArrayList<CartItem> cartItems)
    {
        this.shoppingCart=cartItems;
    }

    public ArrayList<SanPham>getFavoritesList(){return this.favoritesList;}

    public void setFavoritesList(ArrayList<SanPham> list)
    {
        this.favoritesList=list;
    }

    public void WriteToFileInternal(ArrayList<SanPham> arrayList)
    {
        try
        {
            File file=new File(context.getFilesDir(),filename);
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(arrayList);

            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<SanPham> LoadFileInternal()
    {
        ArrayList<SanPham> arrayList2=new ArrayList<>();
        try
        {
            File file=new File(context.getFilesDir(),filename);
            FileInputStream fileInputStream=new FileInputStream(file);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);

            arrayList2=(ArrayList<SanPham>)objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return arrayList2;
    }
}
