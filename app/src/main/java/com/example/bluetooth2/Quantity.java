package com.example.bluetooth2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Quantity extends AppCompatActivity {

    int pressedButtonNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);

        pressedButtonNumber = getIntent().getExtras().getInt("buttonNumber");
        TextView tv1 = (TextView)findViewById(R.id.textView);
        switch(pressedButtonNumber){
            case 1:
                tv1.setText(getString(R.string.btn_shampoo));
                System.err.println("!!! Button 1" + getString(R.string.btn_shampoo));
                break;
            case 2:
                tv1.setText(getString(R.string.btn_antiseptic));
                System.err.println("!!! Button 2");
                break;
            case 3:
                tv1.setText(getString(R.string.btn_liquid_soap));
                System.err.println("!!! Button 3");
                break;
            case 4:
                tv1.setText(getString(R.string.btn_dish_soap));
                System.err.println("!!! Button 4");
                break;
        }
    }

    public void goToMain(View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}