package com.example.bluetooth2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bluetooth2.dao.Order;

import androidx.appcompat.app.AppCompatActivity;

public class Quantity extends AppCompatActivity {

    //TODO binding
    Order order;
    Button btnNext;
    int step = 100;
    double priceFor100ml, max_count = 10000.0, priceBottle;
    TextView tv_count, tv_price, tv_max_count, tv_price_for_count, tv_selected_product_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);

        tv_selected_product_name = (TextView) findViewById(R.id.selectedProductName);
        tv_count = (TextView) findViewById(R.id.integer_number);
        tv_price = (TextView) findViewById(R.id.textViewPriceForRefilling);
        tv_max_count = (TextView) findViewById(R.id.textViewAvailableQuantity);
        tv_price_for_count = (TextView) findViewById(R.id.textViewPriceToPay);
        btnNext = (Button) findViewById(R.id.btnNext);

        order = (Order) getIntent().getSerializableExtra("order");
        if(order != null){
            tv_count.setText("" + order.getAmount());
            tv_price_for_count.setText(order.getPrice() + "  тг");
        } else {
            order = new Order();
            order.setProductId(getIntent().getExtras().getInt("buttonNumber"));
        }


        //TODO set properly max_count
        tv_max_count.setText("" + max_count);


        priceBottle = Double.parseDouble(getString(R.string.price_bottle));

        switch (order.getProductId()) {
            case 1:
                priceFor100ml = Double.parseDouble(getString(R.string.price_product1_100ml));

                tv_selected_product_name.setText(getString(R.string.name_product1));
                tv_price.setText(getString(R.string.price_product1_100ml) + "  тг");
                break;
            case 2:
                priceFor100ml = Double.parseDouble(getString(R.string.price_product1_100ml));

                tv_selected_product_name.setText(getString(R.string.name_product2));
                tv_price.setText(getString(R.string.price_product2_100ml) + "  тг");
                break;
            case 3:
                priceFor100ml = Double.parseDouble(getString(R.string.price_product1_100ml));

                tv_selected_product_name.setText(getString(R.string.name_product3));
                tv_price.setText(getString(R.string.price_product3_100ml) + "  тг");
                break;
            case 4:
                priceFor100ml = Double.parseDouble(getString(R.string.price_product1_100ml));

                tv_selected_product_name.setText(getString(R.string.name_product4));
                tv_price.setText(getString(R.string.price_product4_100ml) + "  тг");
                break;
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.getPrice() > 0.0) {
                    Intent intent = new Intent(v.getContext(), Payment.class);
                    //There is no limit for number of Extras you want to pass to activity
                    intent.putExtra("order", order);
                    startActivity(intent);
                }
            }
        });
    }

    public void goToMainFromQuantity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        System.out.println("onRadioButtonClicked: " + view.getId());

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

        switch (v.getId()) {
            case R.id.increase:
                if(max_count >= order.getAmount() + step) {
                    order.increaseCountBy(step);
                    order.calculatePrice(priceFor100ml);

                    tv_count.setText("" + order.getAmount());
                    tv_price_for_count.setText(order.getPrice() + "  тг");

                }
                break;
            case R.id.decrease:
                if(0 <= order.getAmount() - step) {
                    order.decreaseCountBy(step);
                    order.calculatePrice(priceFor100ml);

                    tv_count.setText("" + order.getAmount());
                    tv_price_for_count.setText(order.getPrice() + "  тг");
                }
                break;
            default:
                break;
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBoxBottle:
                if (checked) {
                    order.addToPrice(priceBottle);
                }
                else {
                    order.reduceFromPrice(priceBottle);
                }
                tv_price_for_count.setText(order.getPrice() + "  тг");
                break;
        }
    }
}