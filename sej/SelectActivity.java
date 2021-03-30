package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {

    private Intent intent;
    private ArrayList<String> names;

    private ListView listView;
    private ArrayAdapter adapter;
    private Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        setTitle("지역 선택");
        setResult(0);
        intent = getIntent();
        names = intent.getStringArrayListExtra("Names");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        listView = findViewById(R.id.slist);
        listView.setAdapter(adapter);
        filter = adapter.getFilter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("Selected", strText);
                setResult(1, intent);
                Log.i("touch", strText);
                finish();
            }
        });

        SearchView searchView = findViewById(R.id.searcher);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter.filter(newText);
                return true;
            }
        });
    }


}
