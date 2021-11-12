package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }
    EditText edtMKCu,edtMKMoi,edtXN_MKMoi;
    Button btnLuu;
    User user;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Lịch sử xem sản phẩm");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        Intent intent=getActivity().getIntent();
        user=(User)intent.getSerializableExtra("User");

        edtMKCu=view.findViewById(R.id.editTextMK_Cu);
        edtMKMoi=view.findViewById(R.id.editTextMK_Moi);
        edtXN_MKMoi=view.findViewById(R.id.editTextXacNhan_MK_Moi);
        btnLuu=view.findViewById(R.id.btnThayMatKhau);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMKMoi.getText().toString().isEmpty()||edtMKCu.getText().toString().isEmpty()||edtXN_MKMoi.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(),"Hãy nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!user.getPass().equals(edtMKCu.getText().toString()))
                {
                    edtMKCu.setError("Sai mật khẩu");
                    return;
                }
                if(edtMKMoi.getText().length()<6)
                {
                    edtMKMoi.setError("Mật khẩu phải có 6 ký tự");
                    return;
                }
                if(edtMKMoi.getText().toString().equals(edtXN_MKMoi.getText().toString()))
                {
                    putPassword();
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Prefer", Context.MODE_PRIVATE).edit();
                    editor.putString("phoneKey", edtMKMoi.getText().toString().trim());
                    editor.commit();

                    user.setPass(edtMKMoi.getText().toString().trim());
                    intent.putExtra("User", user);
                }
                else
                {
                    edtXN_MKMoi.setError("Mật khẩu không trùng khớp");
                    return;
                }
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
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
            }
            else {
                getActivity().onBackPressed();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void putPassword()
    {
        String url="http://10.0.2.2:5000/api/TaiKhoan/UpdatePassword/"+user.getMakh();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("password",edtMKMoi.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, object,
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
}