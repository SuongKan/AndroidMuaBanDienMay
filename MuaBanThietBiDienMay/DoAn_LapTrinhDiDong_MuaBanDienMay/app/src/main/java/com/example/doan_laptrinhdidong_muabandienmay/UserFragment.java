package com.example.doan_laptrinhdidong_muabandienmay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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

    ImageView img;
    TextView txtName;
    TextView txtEmail;
    LinearLayout layoutMuaHang;
    LinearLayout layoutHistory;
    LinearLayout layoutFavories;
    LinearLayout layoutUpdateUser;
    LinearLayout layoutChanggePass;
    LinearLayout layoutDangXuat;
    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;

    private boolean allowRefresh = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent=getActivity().getIntent();
        user=(User)intent.getSerializableExtra("User");

        img=view.findViewById(R.id.imageUser);
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.user_1);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        img.setImageDrawable(roundedBitmapDrawable);

        txtName=view.findViewById(R.id.txtUserName);
        txtEmail=view.findViewById(R.id.txtEmail);
        txtName.setText(user.getUsername());
        txtEmail.setText(user.getEmail());

        layoutMuaHang=view.findViewById(R.id.layoutMuaHang);
        layoutMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.replace(R.id.nav_host_fragment_framelayout,new HoaDonFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        layoutHistory=view.findViewById(R.id.layoutHistory);
        layoutHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.replace(R.id.nav_host_fragment_framelayout,new HistoryFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        layoutFavories=view.findViewById(R.id.layoutFavories);
        layoutFavories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.replace(R.id.nav_host_fragment_framelayout,new FavotiresFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        layoutUpdateUser=view.findViewById(R.id.layoutCapNhatThongTin);
        layoutUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.replace(R.id.nav_host_fragment_framelayout,new InfoFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        layoutChanggePass=view.findViewById(R.id.layoutThayMatKhau);
        layoutChanggePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.replace(R.id.nav_host_fragment_framelayout,new ChangePasswordFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        layoutDangXuat=view.findViewById(R.id.layoutDangXuat);
        layoutDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences2=getActivity().getSharedPreferences(LoginActivity.UserPrefer,Context.MODE_PRIVATE);
                sharedPreferences2.edit().remove("nameKey").apply();
                sharedPreferences2.edit().remove("phoneKey").apply();
                String name=sharedPreferences2.getString("nameKey",null);

                Intent intent1=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(allowRefresh) {
            allowRefresh=false;
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            Intent intent=getActivity().getIntent();
            user=(User)intent.getSerializableExtra("User");
            txtName.setText(user.getUsername());
            txtEmail.setText(user.getEmail());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(!allowRefresh)
        {
            allowRefresh=true;
        }
    }
}