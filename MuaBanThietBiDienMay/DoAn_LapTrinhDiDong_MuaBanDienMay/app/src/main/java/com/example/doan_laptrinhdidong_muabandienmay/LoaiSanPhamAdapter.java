package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class LoaiSanPhamAdapter extends ArrayAdapter<LoaiSanPham> {
    public LoaiSanPhamAdapter(@NonNull Context context, @NonNull List<LoaiSanPham> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LoaiSanPham categories=getItem(position);
        //Furniture furniture=getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.cate_gridview,parent,false);

        ImageView imgCate=convertView.findViewById(R.id.imageCate);
        TextView txtCate=convertView.findViewById(R.id.textView2);

        imgCate.setImageBitmap(LoaiSanPham.convertStringToBitMapFromAccess(getContext(),categories.getImage()));
        txtCate.setText(categories.getName());

        return convertView;
    }
}
