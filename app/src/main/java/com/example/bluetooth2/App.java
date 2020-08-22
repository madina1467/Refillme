package com.example.bluetooth2;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.telephony.TelephonyManager;

import java.io.IOException;

import androidx.core.app.ActivityCompat;

public class App extends Application {
    private static Resources resources;
    protected volatile static String uuid = ""; // 357750044630126

    @Override
    public void onCreate() {
        super.onCreate();
        resources = getResources();
        try {
            uuid = imei();
            System.err.println("!!!! uuid: " + uuid);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static Resources getAppResources() {
        return resources;
    }

    public String imei() throws IOException {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            throw new IOException("!!!! uuid: IMEI permission Denied");
        }
        return telephonyManager.getImei();
    }
}