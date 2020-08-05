package com.example.bluetooth2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bluetooth2.dao.Order;

public class Qrcode extends AppCompatActivity {

    Order order;
    Button btnNext, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);


        order = (Order) getIntent().getSerializableExtra("order");

        btnNext = (Button) findViewById(R.id.btnNext);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Payment.class);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });
    }

    public void goToMainFromPayment(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}