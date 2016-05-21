package com.dotnet.smugglercamp.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dotnet.smugglercamp.Item;
import com.dotnet.smugglercamp.R;
import com.dotnet.smugglercamp.database.DatabaseHelper;
import com.dotnet.smugglercamp.database.ItemsDownloadedEvent;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        enableButtons(false);
    }


    @Subscribe
    public void react(ItemsDownloadedEvent event) {
        downloadedItems = databaseHelper.getItems();
        enableButtons(true);
        Toast.makeText(this, "Event says : " + event.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void add(View view) {

        try {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

            startActivityForResult(intent, 0);

        } catch (ActivityNotFoundException e) {
            try {
                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android.SCAN");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                startActivity(marketIntent);

            } catch (Exception ex) {
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
            }
        }
    }

    public void sell(View view) {
        Intent intent = new Intent(MainActivity.this, SellActivity.class);
        startActivity(intent);
    }

    public void products(View view) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        intent.putExtra("items", (Serializable) downloadedItems);
        startActivity(intent);
    }

    public void bossMode(View view) {
        Intent intent = new Intent(MainActivity.this, BossModeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Map jsonJavaRootObject = new Gson().fromJson(contents, Map.class);

                boolean conId = jsonJavaRootObject.containsKey("item_id");
                boolean conQuantity = jsonJavaRootObject.containsKey("quantity");
                if (conId && conQuantity) {
                    try {
                        int id       = Integer.parseInt((String) jsonJavaRootObject.get("item_id"));
                        int quantity = Integer.parseInt((String) jsonJavaRootObject.get("quantity"));

                        int[] ids = databaseHelper.getIds();
                        int index = Arrays.asList(ids).indexOf(id);
                        if (index == -1) {
                            startAddActivity(id, quantity, null, null);
                        } else {
                            String name = databaseHelper.getName(index);
                            String codename = databaseHelper.getCodeName(index);
                            startAddActivity(id, quantity, name, codename);
                        }
                    } catch (Exception e) {
                        Log.d("Exception", "Exception " + e.getMessage());
                    }
                }
            }
        }
    }

    // do intow
    private void startAddActivity(int id, int quantity, String name, String codename) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("quantity", quantity);
        if (name != null && codename != null) {
            intent.putExtra("name", name);
            intent.putExtra("codename", codename);
        }
        startActivity(intent);
    }

    private void startAddActivity(String... data) {
        if (data.length >= 2 && data.length <= 4) {
            Intent intent = new Intent(this, AddActivity.class);
            intent.putExtra("id", data[0]);
            intent.putExtra("quantity", data[1]);
            if (data.length == 4) {
                intent.putExtra("name", data[2]);
                intent.putExtra("codename", data[3]);
            }
            startActivity(intent);
        }
    }

    private void enableButtons(boolean enable) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean bossMode = sharedPreferences.getBoolean("bossMode", false);

        if(!bossMode) {
            addButton.setEnabled(bossMode);
            sellButton.setEnabled(bossMode);
        } else {
            addButton.setEnabled(enable);
            sellButton.setEnabled(enable);
        }
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



