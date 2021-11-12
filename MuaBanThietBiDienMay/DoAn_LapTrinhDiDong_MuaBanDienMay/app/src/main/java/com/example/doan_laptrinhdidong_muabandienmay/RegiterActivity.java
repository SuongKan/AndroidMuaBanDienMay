package com.example.doan_laptrinhdidong_muabandienmay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.regex.Pattern;

public class RegiterActivity extends AppCompatActivity {
    Button btnSignIn,btnCancel;
    EditText txtEmail,txtUsername,txtPass,txtConfrimPass;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String gioiTinh;
    ArrayList<User> mListUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);
        getSupportActionBar().hide();

        btnCancel=findViewById(R.id.btnCancel);
        btnSignIn=findViewById(R.id.btnSignIn);

        txtEmail=findViewById(R.id.txtEmail);
        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(v);
                }
            }
        });
        txtUsername=findViewById(R.id.txtUserRegister);
        txtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(v);
                }
            }
        });
        txtPass=findViewById(R.id.txtPassword);
        txtPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(v);
                }
            }
        });
        txtConfrimPass=findViewById(R.id.txtConfirmPass);
        txtConfrimPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(v);
                }
            }
        });

        radioGroup=findViewById(R.id.radiusGioiTinh);
        radioButton=findViewById(R.id.radioMale);
        radioButton.setChecked(true);
        gioiTinh=radioButton.getText().toString();

        mListUser=new ArrayList<>();
        getAPIUser();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioMale:
                        radioButton=findViewById(R.id.radioMale);
                        gioiTinh=radioButton.getText().toString();
                        break;
                    case R.id.radioFemale:
                        radioButton=findViewById(R.id.radioFemale);
                        gioiTinh=radioButton.getText().toString();
                        break;
                }
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                if(!isValid(txtEmail.getText().toString())){
                    txtEmail.setError("Invalid Email Address");
                    return;
                }
                if(txtUsername.getText().toString().isEmpty()){
                    txtUsername.setError("Username cannot be null ");
                    return;
                }
                if(txtPass.getText().toString().isEmpty()){
                    txtPass.setError("Password required");
                    return;
                }
                if(txtConfrimPass.getText().toString().isEmpty()){
                    txtConfrimPass.setError("Password required");
                    return;
                }
                if(txtPass.getText().toString().length()<6)
                {
                    txtPass.setError("Mininum 6");
                    return;
                }
                if(txtPass.getText().toString().equals(txtConfrimPass.getText().toString()))
                {
                    if(KiemTraUsername(txtUsername.getText().toString())==false) {
                        postUser();
                        Intent intent = new Intent(RegiterActivity.this,LoginActivity.class);
                        intent.putExtra("username",txtUsername.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(RegiterActivity.this,"Username đã tồn tại",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    txtPass.setError("Password and confirm password does not match");
                    txtConfrimPass.setText("");
                    return;
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                finish();
            }
        });
    }
    public static boolean isValid(String email)
    {
        String emailRegex="^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void postUser() {
        String url="http://10.0.2.2:5000/api/TaiKhoan";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("username",txtUsername.getText().toString());
            object.put("password",txtPass.getText().toString());
            object.put("email",txtEmail.getText().toString());
            object.put("diaChi","value");
            object.put("gioiTinh",gioiTinh);
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
    public void getAPIUser() {
        String url="http://10.0.2.2:5000/api/TaiKhoan";

        RequestQueue requestQueue = Volley.newRequestQueue(RegiterActivity.this);
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

    private boolean KiemTraUsername(String username)
    {
        boolean kq=false;
        for (User u:mListUser) {
            if(u.getUsername().equals(username))
            {
                kq=true;
            }
        }
        return kq;
    }

}