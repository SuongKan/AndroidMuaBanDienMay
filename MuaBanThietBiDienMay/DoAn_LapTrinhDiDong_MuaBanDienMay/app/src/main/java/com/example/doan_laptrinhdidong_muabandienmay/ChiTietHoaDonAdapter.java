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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ChiTietHoaDonAdapter extends ArrayAdapter<ChiTietHoaDon> {
    public ChiTietHoaDonAdapter(@NonNull Context context, @NonNull List<ChiTietHoaDon> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChiTietHoaDon chiTietHoaDon=getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_cthd,parent,false);
        TextView txtTenSP=convertView.findViewById(R.id.txtTenSP_CTHD);
        TextView txtLoaiSP=convertView.findViewById(R.id.txtLoaiSP_CTHD);
        TextView txtDonGia=convertView.findViewById(R.id.txtDonGiaSP_CTHD);
        TextView txtSoLuong=convertView.findViewById(R.id.txtSoLuong_CTHD);
        TextView txtTongTien=convertView.findViewById(R.id.txtTongTien_CTHD);

        txtTenSP.setText(chiTietHoaDon.getTenSP());
        txtLoaiSP.setText(chiTietHoaDon.getLoaiSP());
        txtSoLuong.setText("X "+chiTietHoaDon.getSoLuong());

        NumberFormat formatter = new DecimalFormat("#,###đ");
        String formattedNumber = formatter.format(chiTietHoaDon.getDonGia());
        txtDonGia.setText(formattedNumber);

        String formattedTongTien = formatter.format(chiTietHoaDon.getTongTien());
        txtTongTien.setText(formattedTongTien);

        ImageView img=convertView.findViewById(R.id.imgCTHD);
        img.setImageBitmap(SanPham.convertStringToBitMapFromAccess(getContext(),chiTietHoaDon.getHinhAnh()));
        return convertView;
    }
}
