package com.example.bluetooth2.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.example.bluetooth2.Qrcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class PostMethod extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;
    private final WeakReference<ImageView> imageViewReference;
    String server_response;
    String action = "";
    String company = "";

    private Context mContext;

    public PostMethod(Qrcode activity) {
        dialog = new ProgressDialog(activity);
        mContext = activity;
        imageViewReference = null;
    }

    public PostMethod(Qrcode activity, ImageView imageView) {
        dialog = new ProgressDialog(activity);
        mContext = activity;
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected void  onPreExecute() {
        super.onPreExecute();
//        dialog = new ProgressDialog(mContext); //change is here
        dialog.setMessage("Wait, it's authorization!");
        dialog.setTitle("Connecting... ");
        dialog.show();
//        dialog.setCancelable(false);
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            action = strings[0];
            company = strings[1];

            ApiData apiData;

            if("senim".equals(company)) {
                apiData = new ApiDataSenim(action);
            } else {
                apiData = new ApiDataRahmet(action);
            }

            if("check".equals(action)) {
                callGetApi(apiData.getURL(), apiData.getParams(), apiData.getRequestMethod());
            } else {
                callApi(apiData.getURL(), apiData.getParams(), apiData.getRequestMethod());
            }
            if("auth".equals(action)) {
                ApiData.setToken(server_response);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return server_response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("Response", "" + server_response);

        dialog.cancel();

//        Bitmap bitmap = decodeSampledBitmapFromResource(IconCompat.getResources(), data, 100, 100));

        try {
            String base = (String) CallAPI.getAPIResult(server_response).get("image");
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);

            System.out.println("imageViewReference: " + imageViewReference);
            System.out.println("base: " + base);
            System.out.println("imageAsBytes: " + imageAsBytes);
            System.out.println("url: " + (String) CallAPI.getAPIResult(server_response).get("url"));

            if (imageViewReference != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        AlertDialog.Builder ac = new AlertDialog.Builder(mContext);
//        ac.setTitle("Result");
//        ac.setMessage("Details Successfully Inserted");
//        ac.setCancelable(true);
//
//        ac.setPositiveButton(
//                "Ok",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//
//                    }
//                });
//
//        AlertDialog alert = ac.create();
//        alert.show();
    }

    public void callGetApi(String httpURL, HashMap<String, String> params, String requestMethod) throws IOException {

        System.out.println("GET GET GET: " + action);
        URL url = null;
        try {
            url = new URL(httpURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        if(!"auth".equals(action) && !"".equals(ApiData.token)) {
            conn.setRequestProperty("Authorization", ApiData.token);
        }
//        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null;) {
            builder.append(line).append("\n");
        }
        reader.close();
        conn.disconnect();
        server_response = builder.toString();
    }


    public void callApi(String httpURL, HashMap<String, String> params, String requestMethod) throws IOException {
        URL url = null;
        try {
            url = new URL(httpURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Set set = params.entrySet();
        Iterator i = set.iterator();
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {

            System.out.println("!!! Action: " + action + "; " + param.getKey() + "; " + param.getValue());
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod(requestMethod);

        if("senim".equals(company)) {
//            conn.setRequestProperty("Content-Type", "application/json");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("X-Senim-API-Token", "o9mubdh7ri5gdabbjvl89el1c0");
        } else {
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if(!"auth".equals(action) && !"".equals(ApiData.token)) {
                conn.setRequestProperty("Authorization", ApiData.token);
            }
        }
//        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null;) {
            builder.append(line).append("\n");
        }
        reader.close();
        conn.disconnect();
        server_response = builder.toString();
    }
}



