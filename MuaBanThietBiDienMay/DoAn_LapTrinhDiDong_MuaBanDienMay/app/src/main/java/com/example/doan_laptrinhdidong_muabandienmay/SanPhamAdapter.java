package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {

    Utils utils;
    public SanPhamAdapter(@NonNull Context context, @NonNull List<SanPham> objects) {
        super(context, 0, objects);
        utils=new Utils();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SanPham fr=getItem(position);

        convertView= LayoutInflater.from(getContext()).inflate(R.layout.sanpham_listview,parent,false);

        TextView txtNameFurni = convertView.findViewById(R.id.NameFurni);
        TextView txtDesFurni = convertView.findViewById(R.id.DescripFurni);
        TextView txtGiaTien=convertView.findViewById(R.id.GiaTien);
        ImageView imageFurni = convertView.findViewById(R.id.imgFurni);
        txtNameFurni.setText(fr.getName());
        txtDesFurni.setText(fr.getMoTa());

        NumberFormat formatter = new DecimalFormat("#,###đ");
        String formattedNumber = formatter.format(fr.getGiaTien());
        txtGiaTien.setText(formattedNumber);

        imageFurni.setImageBitmap(SanPham.convertStringToBitMapFromAccess(getContext(),fr.getImage()));

        Button btnFavories=convertView.findViewById(R.id.buttonFavories);
        btnFavories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!utils.isFavorites(getItem(position))) {
                    Toast.makeText(getContext(), "Like " + getItem(position).getName(), Toast.LENGTH_SHORT).show();
                    utils.addFavoritesList(getItem(position));
                    btnFavories.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24);
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Dislike " + getItem(position).getName(), Toast.LENGTH_SHORT).show();
                    utils.removeFavoritesList(getItem(position));
                    btnFavories.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    notifyDataSetChanged();
                }
            }
        });

        if(utils.isFavorites(fr)) {
            btnFavories.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24);
        }
        else {
            btnFavories.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        }

        return convertView;
    }
}
