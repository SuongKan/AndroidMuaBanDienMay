package com.example.doan_laptrinhdidong_muabandienmay;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SanPhamTheoLoaiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SanPhamTheoLoaiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SanPhamTheoLoaiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoaiSanPhamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SanPhamTheoLoaiFragment newInstance(String maLoai,String tenLoai) {

        Bundle args = new Bundle();
        args.putString("category",maLoai);
        args.putString("categoryName",tenLoai);
        SanPhamTheoLoaiFragment fragment = new SanPhamTheoLoaiFragment();
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

    ListView listView;
    ArrayList<SanPham> arrayList;
    SanPhamAdapter sanPhamAdapter;
    int position;
    String maLoai;
    Utils utils;

    public SanPhamTheoLoaiFragment(String maLoai) {
        this.maLoai=maLoai;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loai_san_pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle=getArguments();
        utils=new Utils(getContext());
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(bundle.getString("categoryName"));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        listView=view.findViewById(R.id.lisViewCate);
        arrayList=new ArrayList<SanPham>();

        sanPhamAdapter=new SanPhamAdapter(getContext(),arrayList);
        listView.setAdapter(sanPhamAdapter);
        getAPISanPhamTheoLoai(bundle.getString("category"));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), position+"", Toast.LENGTH_SHORT).show();
                utils.addHistory(arrayList.get(position));
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("sanpham",arrayList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            if (getFragmentManager().getBackStackEntryCount() > 0 ) {
                getFragmentManager().popBackStack();
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                return true;
            }
            else {
                getActivity().onBackPressed();
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        sanPhamAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of SanPhamTheoLoaiFragment");
        utils.saveFavoriesList();
        utils.WriteToFileInternal(arrayList);
        super.onPause();
    }

    public void getAPISanPhamTheoLoai(String maLoai) {
        String url="http://10.0.2.2:5000/api/LoaiSanPham/"+maLoai;

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
}