package com.dotnet.smugglercamp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private TextInputEditText editTextId, editTextCodename, editTextName, editTextQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        // podpinanie widoków
        editTextId          = (TextInputEditText) findViewById(R.id.input_id);
        editTextCodename    = (TextInputEditText) findViewById(R.id.input_codename);
        editTextName        = (TextInputEditText) findViewById(R.id.input_name);
        editTextQuantity    = (TextInputEditText) findViewById(R.id.input_quantity);
    }

    public void addItem(View view) {
        // metoda do obsługi kliknięć na addButton
        Toast.makeText(this, "Hello!", Toast.LENGTH_SHORT).show();
    }
}
