package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }


    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText edtEmail,edtDiaChi;
    String gioitinh;
    User user;
    Button btnSave;
    ArrayList<User> mListUser;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
            }
        });

        mListUser=new ArrayList<>();
        getAPIUser();

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Cập nhật thông tin");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        Intent intent = getActivity().getIntent();
        user = (User) intent.getSerializableExtra("User");

        radioGroup=view.findViewById(R.id.radioGroupGioiTinh);
        if(user.getGioitinh().equals("Male"))
        {
            radioButton=view.findViewById(R.id.radioButtonMale);
        }else if(user.getGioitinh().equals("Female"))
        {
            radioButton=view.findViewById(R.id.radioButtonFemale);
        }

        radioButton.setChecked(true);
        gioitinh=radioButton.getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButtonMale:
                        radioButton=view.findViewById(R.id.radioButtonMale);
                        gioitinh=radioButton.getText().toString();
                        break;
                    case R.id.radioButtonFemale:
                        radioButton=view.findViewById(R.id.radioButtonFemale);
                        gioitinh=radioButton.getText().toString();
                        break;
                }
            }
        });
        edtEmail=view.findViewById(R.id.editTextEmail);
        edtDiaChi=view.findViewById(R.id.editTextDiaChi);

        edtEmail.setText(user.getEmail());
        if((user.getDiachi()!=null)&&(!user.getDiachi().isEmpty())){
            edtDiaChi.setText(user.getDiachi());
        }

        btnSave=view.findViewById(R.id.btnCapNhatUser);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putUser();
                User tmp=getUpdateUser(user.getMakh());
                tmp.setEmail(edtEmail.getText().toString());
                tmp.setDiachi(edtDiaChi.getText().toString());
                tmp.setGioitinh(gioitinh);
                        intent.putExtra("User", tmp);
                        Toast.makeText(getContext(),"Update",Toast.LENGTH_SHORT).show();
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

    public void getAPIUser() {
        String url="http://10.0.2.2:5000/api/TaiKhoan";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("taikhoans");
                    for (int i=0; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        User user=new User();
                        user.setMakh(jsonObject.getInt("maTk"));
                        user.setUsername(jsonObject.getString("username"));
                        user.setPass(jsonObject.getString("password"));
                        user.setEmail(jsonObject.getString("email"));
                        user.setDiachi(jsonObject.getString("diaChi"));
                        user.setGioitinh(jsonObject.getString("gioiTinh"));
                        mListUser.add(user);
                    }
                    Log.i("APIHelper",  mListUser.size()+ "");
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
        Log.i("APIHelper", mListUser.size() + "");
    }

    private void putUser()
    {
        String url="http://10.0.2.2:5000/api/TaiKhoan/UpdateUser/"+user.getMakh();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("email",edtEmail.getText().toString());
            object.put("diaChi",edtDiaChi.getText().toString());
            object.put("gioiTinh",gioitinh);
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

    public User getUpdateUser(int id)
    {
        for (User i :mListUser)
        {
            if(i.getMakh()==id)
            {
                return i;
            }
        }
        return null;
    }


}