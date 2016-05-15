package com.dotnet.smugglercamp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dotnet.smugglercamp.R;
import com.dotnet.smugglercamp.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private Button addButton, sellButton, productsButton, bossModeButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // podpinanie widoków
        addButton       = (Button) findViewById(R.id.addButton);
        sellButton      = (Button) findViewById(R.id.sellButton);
        productsButton  = (Button) findViewById(R.id.productsButton);
        bossModeButton  = (Button) findViewById(R.id.bossModeButton);

        databaseHelper = DatabaseHelper.getInstance();
        databaseHelper.downloadData();

    }

    //metody do obsługi kliknięć na buttony

    public void add(View view) {
        // tworzenie intencji, dzięki której rozpoczniemy nową aktywność
        Intent intent = new Intent(this, AddActivity.class);
        // startowanie nowej aktywności
        startActivity(intent);
    }
    public void sell(View view) {

    }
    public void products(View view) {
        Intent intent = new Intent(this, ProductsActivity.class);
        startActivity(intent);
    }
    public void bossMode(View view) {

    }
}



