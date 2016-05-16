package com.dotnet.smugglercamp.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dotnet.smugglercamp.R;
import com.dotnet.smugglercamp.database.DatabaseHelper;

import java.util.Arrays;


public class SellActivity extends AppCompatActivity {

    private AutoCompleteTextView textView;
    private TextView idTV, codenameTV;
    private TextInputEditText quantityET;
    private Button sellButton;
    private boolean readyToSend = false;
    private String sendName, sendCodename;
    private int sendId;
    private int index;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_layout);
        textView    = (AutoCompleteTextView) findViewById(R.id.nameAutoCompleteTV);
        idTV        = (TextView) findViewById(R.id.textView_id);
        sellButton  = (Button) findViewById(R.id.buttonSell);
        codenameTV  = (TextView) findViewById(R.id.textView_codename);
        quantityET  = (TextInputEditText) findViewById(R.id.textinput_quantity);
        sellButton.setEnabled(false);
        databaseHelper = DatabaseHelper.getInstance();
        getNames();
    }

    private void getNames() {
        final String[] names = databaseHelper.getNames();
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
        textView.setAdapter(autoCompleteAdapter);
        autoCompleteTextViewSettings(names);
    }

    private void autoCompleteTextViewSettings(final String[] dropDownList) {
        textView.setDropDownBackgroundResource(R.color.colorPurple);
        textView.setThreshold(0);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.showDropDown();
            }
        });
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String answerName = textView.getText().toString();
                index = Arrays.asList(dropDownList).indexOf(answerName);

                if (index == -1) {
                    idTV        .setText(" ");
                    codenameTV  .setText(" ");

                    sellButton.setEnabled(false);
                    readyToSend = false;

                } else {
                    sendName        = answerName;
                    sendId          = databaseHelper.getItemId(index);
                    sendCodename    = databaseHelper.getCodeName(index);
                    idTV            .setText(String.valueOf(sendId));
                    codenameTV      .setText(sendCodename);

                    sellButton.setEnabled(true);
                    readyToSend = true;
                }
            }
        });
    }

    public void sellItem(View view) {
        if (readyToSend) {
            int sendQuantity = Integer.parseInt(quantityET.getText().toString());
            if (sendQuantity < databaseHelper.getQuantity(index)) {
                sendQuantity = sendQuantity * (-1);
                databaseHelper.sendData(sendId, sendCodename, sendName, sendQuantity);
                Toast.makeText(this, "You sold " + sendQuantity*(-1) + " items!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                quantityET.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                Toast.makeText(this, "We don't have as many " + databaseHelper.getName(index) + "!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

