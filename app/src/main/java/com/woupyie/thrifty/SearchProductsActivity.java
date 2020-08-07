package com.woupyie.thrifty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SearchProductsActivity extends AppCompatActivity {

    private Button searchButton;
    private EditText inputText;
    private RecyclerView searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        inputText = findViewById(R.id.search_product_name);
        searchButton = findViewById(R.id.search_btn);
        searchList = findViewById(R.id.search_list);

        searchList.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));
    }
}