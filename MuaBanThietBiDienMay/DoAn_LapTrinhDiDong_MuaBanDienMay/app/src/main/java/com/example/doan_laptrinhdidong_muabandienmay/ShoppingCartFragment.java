package com.example.doan_laptrinhdidong_muabandienmay;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.internal.$Gson$Preconditions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingCartFragment newInstance(String param1, String param2) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ArrayList<CartItem> arrayList;
    ShoppingCartAdapter shoppingCartAdapter;
    ListView list;
    Utils utils;
    TextView txtThanhTien;
    TextView txtCart;
    Button btnThanhToan,btnOK_Dia,btnCancel_Dia;
    EditText edtDiaChi;
    User user;
    int maHD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        utils=new Utils(getContext());
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent=getActivity().getIntent();
        user=(User)intent.getSerializableExtra("User");
        list=view.findViewById(R.id.listViewShoppingCart);
        arrayList=utils.getShoppingCart();
        shoppingCartAdapter=new ShoppingCartAdapter(getContext(),arrayList,ShoppingCartFragment.this);
        list.setAdapter(shoppingCartAdapter);

        txtCart=view.findViewById(R.id.shoppingCartTextView);
        if(arrayList.size()>0)
        {
            txtCart.setVisibility(View.GONE);
        }

        txtThanhTien=view.findViewById(R.id.ThanhTien);
        NumberFormat formatter = new DecimalFormat("#,###đ");
        String formattedNumber = formatter.format(utils.TinhTongTien());
        txtThanhTien.setText(formattedNumber);

        btnThanhToan=view.findViewById(R.id.btnThanhToan);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size()==0)
                {
                    Toast.makeText(getContext(),"Hãy thêm sản phẩm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                    return;
                }
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_nhap_dia_chi);

                btnOK_Dia=dialog.findViewById(R.id.btnOK_DiaChi);
                btnCancel_Dia=dialog.findViewById(R.id.btnCancelDia_DiaChi);
                edtDiaChi=dialog.findViewById(R.id.editTextDia_DiaChi);
                edtDiaChi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            hideSoftKeyboard(v);
                        }
                    }
                });

                btnOK_Dia.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if(!edtDiaChi.getText().toString().isEmpty()) {
                                postHoaDOn();

                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getAPI_LastHoaDon();
                            }
                        }, 300);
                        final Handler handler1 = new Handler(Looper.getMainLooper());
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (CartItem i:utils.getShoppingCart()) {
                                    postChiTietHoaDon(i);
                                }
                            }
                        }, 1000);
                        final Handler handler2 = new Handler(Looper.getMainLooper());
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                utils.getShoppingCart().clear();
                                utils.saveShoppingCart();
                                updateAdapter();
                            }
                        }, 1500);
                            dialog.dismiss();
                        }

                    }
                });

                btnCancel_Dia.setOnClickListener(new View.OnClickListener() {
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

    }

    @Override
    public void onPause() {
        utils.saveShoppingCart();
        super.onPause();
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void updateAdapter()
    {
        shoppingCartAdapter.notifyDataSetChanged();
        NumberFormat formatter = new DecimalFormat("#,###đ");
        String formattedNumber = formatter.format(utils.TinhTongTien());
        txtThanhTien.setText(formattedNumber);
        if(arrayList.size()==0)
        {
            txtCart.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void postHoaDOn() {
        String url="http://10.0.2.2:5000/api/HoaDon";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("ngayLap", LocalDate.now());
            object.put("maTk",user.getMakh());
            object.put("diaChi",edtDiaChi.getText().toString());
            object.put("tongTien",utils.TinhTongTien());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("APIHelper","String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("APIHelper","String Response : "+ error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void getAPI_LastHoaDon() {
        String url="http://10.0.2.2:5000/api/HoaDon/GetLastHoaDon/"+user.getMakh();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response;
                    maHD=object.getInt("maHd");
                    Log.i("APIHelper", "Mã HD: "+maHD );
                } catch (JSONException e) {
                    Log.i("APIHelper", e.getMessage());
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("APIHelper", error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
        Log.i("APIHelper", "Mã HD: "+maHD);
    }

    public void postChiTietHoaDon(CartItem item) {
        String url="http://10.0.2.2:5000/api/ChiTietHoaDon";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("maHd", maHD);
            object.put("maSp",item.getSanPham().getSanphamID());
            object.put("donGia",item.getSanPham().getGiaTien());
            object.put("soLuong",item.getSoluong());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("APIHelper","String Response : "+ response.toString()+" Sản phẩm: "+item.getSanPham().getName());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("APIHelper","String Response : "+ error.getMessage()+" Sản phẩm: "+item.getSanPham().getName());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}