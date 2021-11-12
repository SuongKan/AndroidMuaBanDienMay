package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HoaDonAdapTer extends ArrayAdapter<HoaDon> {
    public HoaDonAdapTer(@NonNull Context context, @NonNull List<HoaDon> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        HoaDon hd=getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_hoadon,parent,false);

        TextView txtMaHD=convertView.findViewById(R.id.txtMaHD);
        TextView txtNgayLap=convertView.findViewById(R.id.txtNgayLap);
        TextView txtDiaChi=convertView.findViewById(R.id.txtDiaChi);
        TextView txtTongTien=convertView.findViewById(R.id.txtTongTienHD);

        txtMaHD.setText("Mã hóa đơn: "+hd.getMaHD());
        txtDiaChi.setText("Địa chỉ giao hàng: "+hd.getDiaChi());

        NumberFormat formatter = new DecimalFormat("#,###đ");
        String formattedNumber = formatter.format(hd.getTongTien());
        txtTongTien.setText(formattedNumber);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        txtNgayLap.setText("Ngày mua: "+format.format(hd.getNgayLap()));

        return convertView;
    }
}
