package com.dotnet.smugglercamp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dotnet.smugglercamp.R;
import com.dotnet.smugglercamp.database.DatabaseHelper;

public class AddActivity extends AppCompatActivity {

    private TextInputEditText editTextId, editTextCodename, editTextName, editTextQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String codename = intent.getStringExtra("codename");
        String id = intent.getStringExtra("id");
        String quantity = intent.getStringExtra("quantity");

        editTextId = (TextInputEditText) findViewById(R.id.input_id);
        editTextCodename = (TextInputEditText) findViewById(R.id.input_codename);
        editTextName = (TextInputEditText) findViewById(R.id.input_name);
        editTextQuantity = (TextInputEditText) findViewById(R.id.input_quantity);

        editTextCodename.setText(codename);
        editTextName.setText(name);
        editTextId.setText(id);
        editTextQuantity.setText(quantity);
    }

    private void insertItem() {
        // pobranie z pól tekstowych odpowiednich zmiennych
        int id = Integer.parseInt(editTextId.getText().toString());
        String codename = editTextCodename.getText().toString();
        String name = editTextName.getText().toString();
        int quantity = Integer.parseInt(editTextQuantity.getText().toString());
        // Uzyskanie dostępu do instancji klasy DatabaseHelper
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        // Wywołanie metody dodającej elementy do bazy
        databaseHelper.sendData(id, codename, name, quantity);
        // zakończenie AddActivity -> powrót do widoku MainActivity
        finish();
    }

    public void addItem(View view) {
        // Sprawdzenie czy zostały niewypełnione pola
        if (checkIfEmpty(editTextId) || checkIfEmpty(editTextCodename) || checkIfEmpty(editTextName) || checkIfEmpty(editTextQuantity)) {
            Toast.makeText(AddActivity.this, "Fill all the fields", Toast.LENGTH_LONG).show();
        } else {
            // jeśli wszystkie pola są wypełnione -> wywołujemy metodę insertItem
            insertItem();
        }
    }

    // Funkcja zwracająca TRUE jeśli długość Stringa pobranego z pola tekstowego nie jest większa od zera
    private boolean checkIfEmpty(TextInputEditText editText) {
        return editText.getText().toString().trim().length() <= 0;
    }
}