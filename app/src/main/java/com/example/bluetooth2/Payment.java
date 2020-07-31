package com.example.bluetooth2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class Payment extends AppCompatActivity {

    int pressedButtonNumber;
    boolean payByQR = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        pressedButtonNumber = getIntent().getExtras().getInt("buttonNumber");
        TextView tv1 = (TextView)findViewById(R.id.selectedProductName);
        switch (pressedButtonNumber) {
            case 1:
                tv1.setText(getString(R.string.btn_shampoo));
                break;
            case 2:
                tv1.setText(getString(R.string.btn_antiseptic));
                break;
            case 3:
                tv1.setText(getString(R.string.btn_liquid_soap));
                break;
            case 4:
                tv1.setText(getString(R.string.btn_dish_soap));
                break;
        }
    }


    public void goToMainFromPayment(View view) {
        System.err.println("!!!!!!!!!!!!GO TO MAIN");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.payByQR:
                if (checked)
                    payByQR = true;
                    break;
            case R.id.payByApp:
                if (checked)
                    payByQR = false;
                    break;
        }
    }
}