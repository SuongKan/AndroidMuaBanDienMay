package com.example.doan_laptrinhdidong_muabandienmay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    EditText editUser, editPass;
    private List<User> mListUser;
    private User user;

    public static final String MyPREFERENCES="Prefer";
    public static final String MaTK = "MaTK";
    public static final String Name = "nameKey";
    public static final String Pass = "phoneKey";
    public static final String Email="Email";
    public static final String DiaChi="DiaChi";
    public static final String GioiTinh="GioiTinh";
    public static String UserPrefer="";
    public static String NamePefer="UserName";

    SharedPreferences sharedpreferences;
    SharedPreferences sharedPreferences2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        mListUser=new ArrayList<>();
        getAPIUser();
        getUser();

        Intent intent=getIntent();
        if (user.getUsername()!=null&&user.getPass()!=null) {
            intent = new Intent(LoginActivity.this, ViewMenuActivity.class);
            intent.putExtra("User", user);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);

        editPass=findViewById(R.id.txtPass);
        editPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(v);
                }
            }
        });

        editUser=findViewById(R.id.txtUsername);
        String username=intent.getStringExtra("username");
        editUser.setText(username);

        editUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(v);
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                if(editUser.getText().toString().isEmpty()||
                        editPass.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Mời bạn nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(editPass.getText().toString().length()<6)
                {
                    editPass.setError("Mininum 6");
                }
                else
                {
                    KiemTraDangNhap(editUser.getText().toString(),editPass.getText().toString());
                    if(user!=null)
                    {
                        UserPrefer=user.getUsername();
                        sharedPreferences2 = getSharedPreferences(UserPrefer, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences2.edit();

                        editor.putInt(MaTK,user.getMakh());
                        editor.putString(Name, user.getUsername());
                        editor.putString(Pass, user.getPass());
                        editor.putString(Email,user.getEmail());
                        editor.putString(DiaChi,user.getDiachi());
                        editor.putString(GioiTinh,user.getGioitinh());

                        editor.commit();

                        sharedpreferences=getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
                        editor=sharedpreferences.edit();
                        editor.putString(NamePefer,UserPrefer);
                        editor.commit();

                        Intent intent=new Intent(LoginActivity.this, ViewMenuActivity.class);
                        intent.putExtra("User",user);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Sai username hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                Intent intent=new Intent(LoginActivity.this,RegiterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        getAPIUser();
        super.onRestart();
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void getAPIUser() {
        String url="http://10.0.2.2:5000/api/TaiKhoan";

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("taikhoans");
                    for (int i=0; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                            user=new User();
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
    private void KiemTraDangNhap(String username,String pass)
    {
        user=null;
        for (User u:mListUser) {
            if(u.getUsername().equals(username)&&u.getPass().equals(pass))
            {
                user=u;
            }
        }
    }

    private void getUser()
    {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        UserPrefer=sharedpreferences.getString(NamePefer,null);

        sharedPreferences2=getSharedPreferences(UserPrefer,Context.MODE_PRIVATE);
        String name=sharedPreferences2.getString(Name,null);
        String pass=sharedPreferences2.getString(Pass,null);
        int makh=sharedPreferences2.getInt(MaTK,0);
        String email=sharedPreferences2.getString(Email,null);
        String diaChi=sharedPreferences2.getString(DiaChi,null);
        String gioiTinh=sharedPreferences2.getString(GioiTinh,null);

        user=new User(makh,name,pass,email,diaChi,gioiTinh);

    }

    public SharedPreferences getSharedpreferences() {
        return sharedpreferences;
    }

    public String getMyPREFERENCES() {
        return MyPREFERENCES;
    }

    public String getNamePefer() {
        return NamePefer;
    }
}