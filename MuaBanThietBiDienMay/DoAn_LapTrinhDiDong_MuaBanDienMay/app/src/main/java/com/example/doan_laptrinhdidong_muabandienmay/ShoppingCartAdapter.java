package com.example.doan_laptrinhdidong_muabandienmay;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends ArrayAdapter<CartItem> {
    private ShoppingCartFragment shoppingCartFragment;
    private List<CartItem> cartItemList;
    private Utils utils;
    Button btnOk,btnCancel;
    EditText editTextSoLuong;
    public ShoppingCartAdapter(@NonNull Context context, @NonNull List<CartItem> objects,ShoppingCartFragment fragment) {
        super(context, 0, objects);
        this.shoppingCartFragment=fragment;
        this.cartItemList=objects;
        this.utils=new Utils(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CartItem cartItem=getItem(position);
        //Furniture furniture=getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.shoppingcart_listview,parent,false);

        ImageView img=convertView.findViewById(R.id.imgCartItem);
        TextView txtName=convertView.findViewById(R.id.NameCartItem);
        TextView txtPrice=convertView.findViewById(R.id.priceCartItem);
        TextView txtSoLuong=convertView.findViewById(R.id.soLuongCartItem);
        TextView txtCart=convertView.findViewById(R.id.shoppingCartTextView);
        Button btnDelete=convertView.findViewById(R.id.btnDelete);
        Button btnUpdate=convertView.findViewById(R.id.btnEdit);


        img.setImageBitmap(SanPham.convertStringToBitMapFromAccess(getContext(),cartItem.getSanPham().getImage()));
        NumberFormat formatter = new DecimalFormat("#,###đ");
        String formattedNumber = formatter.format(cartItem.getSanPham().getGiaTien());
        txtPrice.setText(formattedNumber);
        txtName.setText(cartItem.getSanPham().getName());
        txtSoLuong.setText("Số lượng X "+cartItem.getSoluong());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.removeShoppingCart(cartItemList.get(position));
                shoppingCartFragment.updateAdapter();
                utils.saveShoppingCart();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.dislog_edit_quality);

                btnOk=dialog.findViewById(R.id.btnOK);
                btnCancel=dialog.findViewById(R.id.btnCancelDia);
                editTextSoLuong=dialog.findViewById(R.id.editTextSoLuong);
                editTextSoLuong.setText(cartItemList.get(position).getSoluong()+"");
                editTextSoLuong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            hideSoftKeyboard(v);
                        }
                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!editTextSoLuong.getText().toString().isEmpty()) {
                            int sl = Integer.parseInt(editTextSoLuong.getText().toString());
                            if (sl > 0) {
                                cartItemList.get(position).setSoluong(sl);
                                shoppingCartFragment.updateAdapter();
                                utils.saveShoppingCart();
                                dialog.dismiss();
                            }
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        return convertView;
    }
    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
