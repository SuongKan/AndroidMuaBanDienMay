package com.example.doan_laptrinhdidong_muabandienmay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DetailActivity extends AppCompatActivity {

    TextView textDescription;
    TextView textPrice;
    TextView textName;
    ImageView img;
    SanPham sanPham;
    SpannableStringBuilder ssb;
    TextView textSoLuong;
    Button btnTang;
    Button btnGiam;
    Button btnAddToCart;
    Button btnFavories;
    Utils utils;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        String namePrefer=intent.getStringExtra("name");
        sharedPreferences=getSharedPreferences(namePrefer, Context.MODE_PRIVATE);
        utils=new Utils(DetailActivity.this);

        textName=findViewById(R.id.textName);
        textPrice=findViewById(R.id.textPrice);
        textDescription=findViewById(R.id.textDescription);
        img=findViewById(R.id.imageDetails);

        sanPham=(SanPham)intent.getSerializableExtra("sanpham");

        textName.setText(sanPham.getName());

        NumberFormat formatter = new DecimalFormat("#,###đ");
        String formattedNumber = formatter.format(sanPham.getGiaTien());
        textPrice.setText(formattedNumber);


        DescriptionFormat(sanPham.getMoTa());
        textDescription.setText(ssb);
        img.setImageBitmap(SanPham.convertStringToBitMapFromAccess(getBaseContext(),sanPham.getImage()));

        getSupportActionBar().setTitle(sanPham.getName()+"");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnTang=findViewById(R.id.increment);
        btnGiam=findViewById(R.id.decrement);
        btnAddToCart=findViewById(R.id.btnAddCart);
        textSoLuong=findViewById(R.id.display);
        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=Integer.parseInt(textSoLuong.getText().toString());
                textSoLuong.setText(""+(i+1));
            }
        });
        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(textSoLuong.getText().toString());
                if(i>1) {
                    textSoLuong.setText("" + (i - 1));
                }
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem cartItem=new CartItem(sanPham,Integer.parseInt(textSoLuong.getText().toString()));
                utils.addShoppingCart(cartItem);
                utils.saveShoppingCart();
                Toast.makeText(DetailActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });

        btnFavories=findViewById(R.id.btnFavDetails);
        if(utils.isFavorites(sanPham)) {
            btnFavories.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24);
        }
        else {
            btnFavories.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        }
        btnFavories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!utils.isFavorites(sanPham)) {
                    Toast.makeText(DetailActivity.this, "Like " + sanPham.getName(), Toast.LENGTH_SHORT).show();
                    utils.addFavoritesList(sanPham);
                    utils.saveFavoriesList();
                    btnFavories.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24);
                }
                else {
                    Toast.makeText(DetailActivity.this,"Dislike " + sanPham.getName(), Toast.LENGTH_SHORT).show();
                    utils.removeFavoritesList(sanPham);
                    utils.saveFavoriesList();
                    btnFavories.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    private void DescriptionFormat(String description)
    {
        String longDescription = description;

        String arr[] = longDescription.split("\r\n");

        int bulletGap = (int) getResources().getDisplayMetrics().density * 30;

        ssb = new SpannableStringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String line = arr[i];
            SpannableString ss = new SpannableString(line);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ss.setSpan(new BulletSpan(bulletGap,Color.BLACK,20), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            ssb.append(ss);

            //avoid last "\r\n"
            if(i+1<arr.length)
                ssb.append("\r\n");

        }
    }
}