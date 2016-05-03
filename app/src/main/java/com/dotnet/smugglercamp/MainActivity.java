package com.dotnet.smugglercamp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button addButton, sellButton, productsButton, bossModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // podpinanie widoków
        addButton       = (Button) findViewById(R.id.addButton);
        sellButton      = (Button) findViewById(R.id.sellButton);
        productsButton  = (Button) findViewById(R.id.productsButton);
        bossModeButton  = (Button) findViewById(R.id.bossModeButton);
        
    }

    //metody do obsługi kliknięć na buttony

    public void add(View view) {

    }
    public void sell(View view) {

    }
    public void products(View view) {

    }
    public void bossMode(View view) {

    }
}



