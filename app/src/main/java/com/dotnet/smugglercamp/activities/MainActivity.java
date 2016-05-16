package com.dotnet.smugglercamp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dotnet.smugglercamp.Item;
import com.dotnet.smugglercamp.R;
import com.dotnet.smugglercamp.database.DatabaseHelper;
import com.dotnet.smugglercamp.database.ItemsDownloadedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addButton, sellButton, productsButton, bossModeButton;
    private DatabaseHelper databaseHelper;
    private EventBus eventBus;
    private List<Item> downloadedItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addButton);
        sellButton = (Button) findViewById(R.id.sellButton);
        productsButton = (Button) findViewById(R.id.productsButton);
        bossModeButton = (Button) findViewById(R.id.bossModeButton);

        eventBus = EventBus.getDefault();
        eventBus.register(this);
        databaseHelper = DatabaseHelper.getInstance();
    }


    @Subscribe
    public void onEvent(ItemsDownloadedEvent event) {
        downloadedItems = databaseHelper.getItems();
        enableButtons(true);
        Toast.makeText(this, "Event says : " + event.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void add(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void sell(View view) {

    }

    public void products(View view) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        intent.putExtra("items", (Serializable) downloadedItems);
        startActivity(intent);
    }

    public void bossMode(View view) {

    }

    private void enableButtons(boolean enable) {
        addButton.setEnabled(enable);
        sellButton.setEnabled(enable);
        productsButton.setEnabled(enable);
        bossModeButton.setEnabled(enable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        enableButtons(false);
        if (eventBus.isRegistered(this)) eventBus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!eventBus.isRegistered(this)) eventBus.register(this);
        databaseHelper.downloadData();
    }
}



