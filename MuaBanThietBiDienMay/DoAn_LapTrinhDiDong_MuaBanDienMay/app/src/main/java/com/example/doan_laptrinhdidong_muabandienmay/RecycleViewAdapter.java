package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    // Store a member variable for the contacts
    private List<SanPham> sanPhams;
    Context context;
    Utils utils;
    // Pass in the contact array into the constructor
    public RecycleViewAdapter(Context context,List<SanPham> sanPhams) {
        this.sanPhams=sanPhams;
        this.context=context;
    }
    public RecycleViewAdapter(Context context,List<SanPham> sanPhams,Utils utils) {
        this.sanPhams=sanPhams;
        this.context=context;
        this.utils=utils;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontall_recycleview,parent,false);
        RecycleViewAdapter.MyViewHolder vh=new RecycleViewAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.MyViewHolder holder, int position) {
            SanPham sanPham=sanPhams.get(position);
            holder.NameTextView.setText(sanPham.getName());
            NumberFormat formatter = new DecimalFormat("#,###đ");
            String formattedNumber = formatter.format(sanPham.getGiaTien());
            holder.PriceTextView.setText(formattedNumber);
            holder.imageView.setImageBitmap(SanPham.convertStringToBitMapFromAccess(context,sanPham.getImage()));
            holder.position=position;
            holder.favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!utils.isFavorites(sanPham)) {
                        Toast.makeText(context, "Like " + sanPham.getName(), Toast.LENGTH_SHORT).show();
                        utils.addFavoritesList(sanPham);
                        holder.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24);
                        notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, "Dislike " + sanPham.getName(), Toast.LENGTH_SHORT).show();
                        utils.removeFavoritesList(sanPham);
                        holder.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                        notifyDataSetChanged();
                    }
                }
            });

        if(utils.isFavorites(sanPham)) {
            holder.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24);
        }
        else {
            holder.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView NameTextView;
        TextView PriceTextView;
        ImageView imageView;
        Button favButton;
        int position;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            NameTextView=(TextView)itemView.findViewById(R.id.NameRecycle);
            PriceTextView=(TextView)itemView.findViewById(R.id.PriceRecycle);
            imageView=(ImageView) itemView.findViewById(R.id.imgRecycle);
            favButton=(Button) itemView.findViewById(R.id.buttonRecycle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                    utils.addHistory(sanPhams.get(position));
                    Intent intent=new Intent(context,DetailActivity.class);
                    intent.putExtra("sanpham",sanPhams.get(position));
                    context.startActivity(intent);
                }
            });
        }
    }

}
