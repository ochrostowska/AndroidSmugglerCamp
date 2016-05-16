package com.dotnet.smugglercamp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dotnet.smugglercamp.Item;
import com.dotnet.smugglercamp.R;
import com.dotnet.smugglercamp.database.DatabaseHelper;
import com.dotnet.smugglercamp.view.SmugglerRecyclerAdapter;

import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_layout);

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        List<Item> items = databaseHelper.getItems();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        RecyclerView productsRecyclerView = (RecyclerView) findViewById(R.id.smugglerRecyclerView);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SmugglerRecyclerAdapter recyclerAdapter = new SmugglerRecyclerAdapter(items);
        productsRecyclerView.setAdapter(recyclerAdapter);
    }
}
