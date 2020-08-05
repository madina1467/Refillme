package com.example.bluetooth2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.bluetooth2.dao.Order;

public class RahmetSenim extends AppCompatActivity {

    Order order;
    Button btnNext, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rahmet_senim);


        order = (Order) getIntent().getSerializableExtra("order");

        btnNext = (Button) findViewById(R.id.btnNext);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Qrcode.class);
                //There is no limit for number of Extras you want to pass to activity
//                intent.putExtra("price", price);
//                intent.putExtra("buttonNumber", pressedButtonNumber);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Payment.class);
                //There is no limit for number of Extras you want to pass to activity
//                intent.putExtra("price", price);
//                intent.putExtra("buttonNumber", pressedButtonNumber);
                startActivity(intent);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.payByRahmet:
                if (checked)
//                    payByQR = true;
                break;
            case R.id.payBySenim:
                if (checked)
//                    payByQR = false;
                break;
        }
    }

    public void goToMainFromPayment(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}