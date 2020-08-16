package com.example.bluetooth2.rest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bluetooth2.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class GetMethod extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;
    String server_response;
    String action = "";
    String token = "";

    private Context mContext;

    public GetMethod(MainActivity activity) {
        dialog = new ProgressDialog(activity);
        mContext = activity;
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

            ApiData apiData = new ApiDataRahmet(action);
//            System.out.println("!!! Action:" + this.action + "; Token: " +ApiData.token);
            callApi(apiData.getURL(), apiData.getParams(), apiData.getRequestMethod());

            if("auth".equals(action)) {
                ApiData.setRahmetToken(server_response);
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

        AlertDialog.Builder ac = new AlertDialog.Builder(mContext);
        ac.setTitle("Result");
        ac.setMessage("Details Successfully Inserted");
        ac.setCancelable(true);

        ac.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert = ac.create();
        alert.show();
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

            System.out.println("!!! Action: " + action + "; String.valueOf(param.getValue(): " + String.valueOf(param.getValue()));
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
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        if(!"auth".equals(action) && !"".equals(ApiData.token)) {
            conn.setRequestProperty("Authorization", ApiData.token);
            System.out.println("!!!!!!!! Set API token");
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



