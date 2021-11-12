package com.example.doan_laptrinhdidong_muabandienmay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewMenuActivity extends AppCompatActivity {

    BottomNavigationView navView;
    boolean status=false;
    MenuItem menuItem;
    EditText searchView;
    RelativeLayout relativeLayout;
    int startPosition=1;
    String namePrefer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        relativeLayout=findViewById(R.id.relative);
        relativeLayout.setVisibility(View.VISIBLE);

        Intent intent=getIntent();
        namePrefer=intent.getStringExtra("name");

        navView=findViewById(R.id.nav_view);
        loadFragment(new HomeFragment(),1,"HomeFragment");

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navi_home);

        searchView=findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewMenuActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            onNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId())
            {
                case R.id.navi_home:
                    relativeLayout.setVisibility(View.VISIBLE);
                    getSupportActionBar().hide();
                    //getSupportActionBar().setTitle("Home");
                    fragment=new HomeFragment();
                    loadFragment(fragment,1,"HomeFragment");
                    return true;
                case R.id.navi_dash:
                    relativeLayout.setVisibility(View.VISIBLE);
                    getSupportActionBar().hide();
                    //getSupportActionBar().setTitle("Dashboard");
                    fragment=new CategoriesFragment();
                    loadFragment(fragment,2,"CategoriesFragment");
                    return true;
                case R.id.navi_nofi:
                    relativeLayout.setVisibility(View.VISIBLE);
                    getSupportActionBar().hide();
                    //getSupportActionBar().setTitle("Notification");
                    fragment=new ShoppingCartFragment();
                    loadFragment(fragment,3,"ShoppingCart");
                    return true;
                case R.id.navi_account:
                    relativeLayout.setVisibility(View.GONE);
                    getSupportActionBar().show();
                    getSupportActionBar().setTitle("Profile");
                    fragment=new UserFragment();
                    loadFragment(fragment,4,"UserFragment");
                    return true;
            }
            return false;
        }
    };

    private  void loadFragment(Fragment fragment,int newPosition,String tag)
    {
        //load fragment
        if(startPosition < newPosition) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right );
            transaction.replace(R.id.nav_host_fragment_framelayout, fragment,tag);
            transaction.commit();
        }
        if(startPosition > newPosition) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            transaction.replace(R.id.nav_host_fragment_framelayout, fragment,tag);
            transaction.commit();
        }
        else
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_framelayout, fragment,tag);
            transaction.commit();
        }
        startPosition = newPosition;
    }
}