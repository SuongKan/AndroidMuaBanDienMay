package com.example.doan_laptrinhdidong_muabandienmay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    ArrayList<SanPham> arrayList;
    ArrayList<SanPham> arrayList1;
    ArrayList<SanPham> arrayList2;
    RecycleViewAdapter recycleViewAdapter;
    RecycleViewAdapter recycleViewAdapter2;
    TextView link1;
    TextView link2;
    Utils utils;
    SanPhamAdapter sanPhamAdapter;
    String myValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        utils=new Utils(getContext());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getSaveShoppingCart();
        getSaveFavoriesList();
        super.onViewCreated(view, savedInstanceState);

        link1 = (TextView) view.findViewById(R.id.textView3);
        link2 = (TextView) view.findViewById(R.id.textView4);

        link1.setClickable(true);
        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                ListSanPhamFragment fragment=new ListSanPhamFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment_framelayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        link2.setClickable(true);
        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                ListSanPhamFragment fragment=new ListSanPhamFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment_framelayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        arrayList=new ArrayList<>();
        sanPhamAdapter=new SanPhamAdapter(getContext(),arrayList);
        getAPISanPham();

        SliderView sliderView = view.findViewById(R.id.imageSlider);

        SliderAdapter adapter = new SliderAdapter(getContext());
        adapter.renewItems(loadImage());

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.NONE); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview1);
        arrayList1 = new ArrayList<>();

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recycleview2);
        arrayList2 = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recycleViewAdapter = new RecycleViewAdapter(getContext(), arrayList1,utils);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleViewAdapter);
        getAPISanPhamNoiBat();

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recycleViewAdapter2 = new RecycleViewAdapter(getContext(), arrayList2,utils);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(recycleViewAdapter2);
        getAPISanPhamMuaNhieu();
    }

    @Override
    public void onResume() {
        recycleViewAdapter.notifyDataSetChanged();
        recycleViewAdapter2.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of HomeFragment");
        utils.saveFavoriesList();
        utils.WriteToFileInternal(arrayList);
        super.onPause();
    }
    public void getAPISanPham() {
        String url="http://10.0.2.2:5000/api/SanPham";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("sanphams");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        SanPham sanPham=new SanPham();

                        sanPham.setSanphamID(jsonObject.getInt("maSanPham"));
                        sanPham.setName(jsonObject.getString("tenSanPham"));
                        sanPham.setMoTa(jsonObject.getString("moTa"));
                        sanPham.setImage(jsonObject.getString("image"));
                        sanPham.setGiaTien(jsonObject.getInt("giaTien"));
                        sanPham.setSoLuongTon(jsonObject.getInt("soLuongTon"));
                        sanPham.setMaLoai(jsonObject.getString("maLoai"));

                        arrayList.add(sanPham);
                    }
                    sanPhamAdapter.notifyDataSetChanged();
                    Log.i("APIHelper", arrayList.size() + "");
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
        Log.i("APIHelper", arrayList.size() + "");
    }
    public void getAPISanPhamNoiBat() {
        String url="http://10.0.2.2:5000/api/SanPham";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("sanphams");
                    for (int i = 0; i < 4; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        SanPham sanPham=new SanPham();

                        sanPham.setSanphamID(jsonObject.getInt("maSanPham"));
                        sanPham.setName(jsonObject.getString("tenSanPham"));
                        sanPham.setMoTa(jsonObject.getString("moTa"));
                        sanPham.setImage(jsonObject.getString("image"));
                        sanPham.setGiaTien(jsonObject.getInt("giaTien"));
                        sanPham.setSoLuongTon(jsonObject.getInt("soLuongTon"));
                        sanPham.setMaLoai(jsonObject.getString("maLoai"));

                        arrayList1.add(sanPham);
                    }
                    recycleViewAdapter.notifyDataSetChanged();
                    Log.i("APIHelper", arrayList1.size() + "");
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
        Log.i("APIHelper", arrayList1.size() + "");
    }
    public void getAPISanPhamMuaNhieu() {
        String url="http://10.0.2.2:5000/api/SanPham";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("sanphams");
                    for (int i = jsonArray.length()-1; i >= (jsonArray.length()-4); i--) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        SanPham sanPham=new SanPham();

                        sanPham.setSanphamID(jsonObject.getInt("maSanPham"));
                        sanPham.setName(jsonObject.getString("tenSanPham"));
                        sanPham.setMoTa(jsonObject.getString("moTa"));
                        sanPham.setImage(jsonObject.getString("image"));
                        sanPham.setGiaTien(jsonObject.getInt("giaTien"));
                        sanPham.setSoLuongTon(jsonObject.getInt("soLuongTon"));
                        sanPham.setMaLoai(jsonObject.getString("maLoai"));

                        arrayList2.add(sanPham);
                    }
                    recycleViewAdapter2.notifyDataSetChanged();
                    Log.i("APIHelper", arrayList2.size() + "");
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
        Log.i("APIHelper", arrayList2.size() + "");
    }

    public List<String>loadImage()
    {
        ArrayList<String> list=new ArrayList<String>();
        list.add("quangcao_1.png");
        list.add("quangcao_2.png");
        list.add("quangcao_3.png");
        list.add("quangcao_4.png");
        return list;
    }

    public void getSaveFavoriesList()
    {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(LoginActivity.UserPrefer, Context.MODE_PRIVATE);
        try {
            utils.setFavoritesList((ArrayList<SanPham>) ObjectSerializer.deserialize(prefs.getString("FavoriesList"
                    ,ObjectSerializer.serialize(new ArrayList()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getSaveHistoryList()
    {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(LoginActivity.UserPrefer, Context.MODE_PRIVATE);
        try {
            utils.setFurnitureHistory((ArrayList<SanPham>) ObjectSerializer.deserialize(prefs.getString("HistoryList"
                    ,ObjectSerializer.serialize(new ArrayList()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getSaveShoppingCart()
    {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(LoginActivity.UserPrefer, Context.MODE_PRIVATE);
        try {
            utils.setShoppingCart((ArrayList<CartItem>) ObjectSerializer.deserialize(prefs.getString("CartList"
                    ,ObjectSerializer.serialize(new ArrayList()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}