package com.example.bluetooth2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Quantity extends AppCompatActivity {

    int pressedButtonNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);

        pressedButtonNumber = getIntent().getExtras().getInt("buttonNumber");
        switch(pressedButtonNumber){
            case 1:
                System.err.println("!!! Button 1");
                break;
            case 2:
                System.err.println("!!! Button 2");
                break;
            case 3:
                System.err.println("!!! Button 3");
                break;
            case 4:
                System.err.println("!!! Button 4");
                break;
        }
    }

    public void goToMain(View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}