package com.example.bluetooth2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private static final String TAG = "Refill.me";

    Button btnShampoo, btnAntiseptic, btnDishSoap, btnLiquidSoap;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnShampoo = (Button) findViewById(R.id.btnShampoo);
        btnAntiseptic = (Button) findViewById(R.id.btnAntiseptic);
        btnLiquidSoap = (Button) findViewById(R.id.btnLiquidSoap);
        btnDishSoap = (Button) findViewById(R.id.btnDishSoap);

        btnShampoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Quantity.class);
                //There is no limit for number of Extras you want to pass to activity
                intent.putExtra("buttonNumber", 1);
                startActivity(intent);
            }
        });

        btnAntiseptic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Quantity.class);
                intent.putExtra("buttonNumber", 2);
                startActivity(intent);
            }
        });

        btnDishSoap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Quantity.class);
                intent.putExtra("buttonNumber", 3);
                startActivity(intent);
            }
        });

        btnLiquidSoap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Quantity.class);
                intent.putExtra("buttonNumber", 4);
                startActivity(intent);
            }
        });

    }
}


