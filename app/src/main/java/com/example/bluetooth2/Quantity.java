package com.example.bluetooth2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class Quantity extends AppCompatActivity {

    int pressedButtonNumber, count = 0, step = 100, max_count = 10000;
    TextView tv_count, tv_max_count,  tv_count_price, tv_selected_product_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);


        pressedButtonNumber = getIntent().getExtras().getInt("buttonNumber");
        tv_selected_product_name = (TextView) findViewById(R.id.selectedProductName);
        tv_count = (TextView) findViewById(R.id.integer_number);
        tv_max_count = (TextView) findViewById(R.id.textViewAvailableQuantity);

        switch (pressedButtonNumber) {
            case 1:
                tv_selected_product_name.setText(getString(R.string.btn_shampoo));
                break;
            case 2:
                tv_selected_product_name.setText(getString(R.string.btn_antiseptic));
                break;
            case 3:
                tv_selected_product_name.setText(getString(R.string.btn_liquid_soap));
                break;
            case 4:
                tv_selected_product_name.setText(getString(R.string.btn_dish_soap));
                break;
        }
    }

    public void goToMainFromQuantity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonForward10ml:
                if (checked)
                    step = 10;
                break;
            case R.id.radioButtonForward50ml:
                if (checked)
                    step = 50;
                break;
            case R.id.radioButtonForward100ml:
                if (checked)
                    step = 100;
                break;
        }
    }

    public void onClick(View v) {
//        int textview = Integer.parseInt(String.valueOf(findViewById(R.id.integer_number)));
        switch (v.getId()) {
            case R.id.increase:
                if(max_count <= count + step) {
                    count += step;
                    tv_count.setText("" + count);
                }
                break;
            case R.id.decrease:
                if(max_count <= count + step) {
                    count -= step;
                    tv_count.setText("" + count);
                }
                break;
            default:
                break;
        }

    }
}