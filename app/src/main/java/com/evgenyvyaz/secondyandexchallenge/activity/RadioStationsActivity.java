package com.evgenyvyaz.secondyandexchallenge.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evgenyvyaz.secondyandexchallenge.R;

public class RadioStationsActivity extends Activity {

    private Button radioSkazkiBTN;
    private Button childRadioBTN;
    private Button radioRecordBTN;
    private Button okBTN;
    private EditText linkET;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_stations);

        radioSkazkiBTN = (Button) findViewById(R.id.radioSkazkiBTN);
        childRadioBTN = (Button) findViewById(R.id.childRadioBTN);
        radioRecordBTN = (Button) findViewById(R.id.radioRecordBTN);
        okBTN = (Button) findViewById(R.id.okBTN);
        linkET = (EditText) findViewById(R.id.linkET);

        radioSkazkiBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(RadioStationsActivity.this, PlayActivity.class);
                intent.putExtra(PlayActivity.RADIO_LINK, "http://ic7.101.ru:8000/c17_27?userid=0&setst=anej52rmkqmvts4h2c2hf2a3d0&tok=25422394qrfrVY2A%2Br3cXzoYmJQM0g%3D%3D1");
                startActivity(intent);
            }
        });

        childRadioBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(RadioStationsActivity.this, PlayActivity.class);
                intent.putExtra(PlayActivity.RADIO_LINK, "http://ic7.101.ru:8000/v14_1?userid=0&setst=anej52rmkqmvts4h2c2hf2a3d0");
                startActivity(intent);
            }
        });

        radioRecordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(RadioStationsActivity.this, PlayActivity.class);
                intent.putExtra(PlayActivity.RADIO_LINK, "http://ic6.101.ru:8000/c8_1?userid=0&setst=anej52rmkqmvts4h2c2hf2a3d0&tok=25422394qrfrVY2A%2Br0IdZlauwCCKg%3D%3D2");
                startActivity(intent);
            }
        });

        okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = linkET.getText().toString();
                if (!text.equals("")) {
                    intent = new Intent(RadioStationsActivity.this, PlayActivity.class);
                    intent.putExtra(PlayActivity.RADIO_LINK, text);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Введите что-нибудь...", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
