package com.example.bluetooth2;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {
    private static final String TAG = "Refill.me";
    private final int REQUEST_PERMISSION_PHONE_STATE=1;


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

        showPhoneStatePermission();
    }

    private void showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_PHONE_STATE);
            } else {
                requestPermission(Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_PHONE_STATE);
            }
        } else {
            Toast.makeText(this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    System.err.println("111 !!!! uuid: IMEI permission Granted");
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                    System.err.println("222 !!!! uuid: IMEI permission Denied");
                }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permissionName}, permissionRequestCode);
    }
}


