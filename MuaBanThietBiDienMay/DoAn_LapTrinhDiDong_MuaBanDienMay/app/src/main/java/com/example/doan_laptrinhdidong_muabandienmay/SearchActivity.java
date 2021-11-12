package com.example.doan_laptrinhdidong_muabandienmay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import me.gujun.android.taggroup.TagGroup;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    Button btnBack;
    ArrayList<SanPham> arrayList;
    ListView listView;
    SanPhamAdapter sanPhamAdapter;
    Utils utils;
    TagGroup tagGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        LinearLayout linearLayout=findViewById(R.id.search_view);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                searchView.clearFocus();
            }
        });

        utils=new Utils(SearchActivity.this);

        getSupportActionBar().hide();

        arrayList=new ArrayList<>();

        listView=findViewById(R.id.SearchListView);
        sanPhamAdapter=new SanPhamAdapter(SearchActivity.this,arrayList);
        listView.setAdapter(sanPhamAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                utils.addHistory(arrayList.get(position));
                Intent intent=new Intent(SearchActivity.this,DetailActivity.class);
                intent.putExtra("sanpham",arrayList.get(position));
                startActivity(intent);
            }
        });

        searchView=findViewById(R.id.search_view2);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFurniture(newText);
                return false;
            }
        });

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText textView = (EditText) searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);

        tagGroup=findViewById(R.id.tag_group);
        tagGroup.setTags(new String[]{"Tivi","Tủ lạnh","Máy giặt","Samsung","LG"});
        tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                searchView.setQuery(tag,false);
                hideSoftKeyboard(searchView);
            }
        });

        btnBack=findViewById(R.id.arrowBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.saveFavoriesList();
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        utils.saveFavoriesList();
        super.onBackPressed();
    }

    public void searchFurniture(String newText)
    {
        ArrayList<SanPham> tmp=new ArrayList<>();

        ArrayList<SanPham> tmp2=utils.LoadFileInternal();
        for(SanPham sanPham : tmp2)
        {
            if(sanPham.getName().toLowerCase().contains(newText.toLowerCase()))
            {
                tmp.add(sanPham);
            }

        }
        if(tmp.size() > 0){
            sanPhamAdapter.clear();
            sanPhamAdapter.addAll(tmp);
            sanPhamAdapter.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
        }
        else
        {
            listView.setVisibility(View.GONE);
        }
        if(newText.isEmpty()){
            listView.setVisibility(View.GONE);
        }
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}