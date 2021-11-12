package com.example.doan_laptrinhdidong_muabandienmay;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    GridView gridView;
    ArrayList<LoaiSanPham> arrayList;
    LoaiSanPhamAdapter loaiSanphamAdapter;
    Utils utils;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        utils=new Utils(getContext());
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView=view.findViewById(R.id.gridView);
        arrayList=new ArrayList<LoaiSanPham>();
        //arrayList.add(new LoaiSanPham("TV","tivi","MayGiat.jpg"));

        loaiSanphamAdapter=new LoaiSanPhamAdapter(getContext(),arrayList);
        gridView.setAdapter(loaiSanphamAdapter);
        getAPILoaiSanPham();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(getContext(), i+"", Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                LoaiSanPham loai=arrayList.get(i);
                fragmentTransaction.replace(R.id.nav_host_fragment_framelayout
                        ,SanPhamTheoLoaiFragment.newInstance(loai.getLoaiSanPhamID().trim()
                        ,loai.getName().trim()));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void onPause() {
        utils.saveFavoriesList();
        super.onPause();
    }

    public void getAPILoaiSanPham() {
        String url="http://10.0.2.2:5000/api/LoaiSanPham";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("loaisanphams");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LoaiSanPham loaiSanPham = new LoaiSanPham();
                        loaiSanPham.setLoaiSanPhamID(jsonObject.getString("maLoai"));
                        loaiSanPham.setName(jsonObject.getString("tenLoai"));
                        loaiSanPham.setImage(jsonObject.getString("image"));
                        arrayList.add(loaiSanPham);
                    }
                    loaiSanphamAdapter.notifyDataSetChanged();
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