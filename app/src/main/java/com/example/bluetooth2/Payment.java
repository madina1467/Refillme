package com.example.bluetooth2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bluetooth2.dao.Order;

public class Payment extends AppCompatActivity {

    Order order;
    Button btnNext, btnCancel;
    boolean payByQR = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        order = (Order) getIntent().getSerializableExtra("order");

        TextView tv1 = (TextView)findViewById(R.id.selectedProductName);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        switch (order.getProduct()) {
            case 1:
                tv1.setText(getString(R.string.name_product1));
                break;
            case 2:
                tv1.setText(getString(R.string.name_product2));
                break;
            case 3:
                tv1.setText(getString(R.string.name_product4));
                break;
            case 4:
                tv1.setText(getString(R.string.name_product3));
                break;
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payByQR) {
                    Intent intent = new Intent(v.getContext(), Qrcode.class);
                    //There is no limit for number of Extras you want to pass to activity
                    intent.putExtra("order", order);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(v.getContext(), RahmetSenim.class);
                    //There is no limit for number of Extras you want to pass to activity
                    intent.putExtra("order", order);
                    startActivity(intent);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Quantity.class);
                //There is no limit for number of Extras you want to pass to activity
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });
    }


    public void goToMainFromPayment(View view) {
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