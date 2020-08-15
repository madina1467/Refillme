package com.example.bluetooth2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bluetooth2.dao.Order;
import com.example.bluetooth2.rest.CallAPI;

public class Qrcode extends AppCompatActivity {

    Order order;
    Button btnNext, btnCancel;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);


        order = (Order) getIntent().getSerializableExtra("order");

        btnNext = (Button) findViewById(R.id.btnNext);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        imageView = (ImageView) findViewById(R.id.qrImage);



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

        apiCall();
    }

    public void goToMainFromPayment(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void apiCall(){
        CallAPI callAPI = new CallAPI();
        callAPI.callAuthAPI(Qrcode.this, imageView);
    }
}