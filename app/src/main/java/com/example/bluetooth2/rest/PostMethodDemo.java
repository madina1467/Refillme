package com.example.bluetooth2.rest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bluetooth2.MainActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

public class PostMethodDemo extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;
    String server_response;
    String token;

    private Context mContext;
//    public PostMethodDemo (Context context){
//        mContext = context;
//    }

    public PostMethodDemo(MainActivity activity) {
        dialog = new ProgressDialog(activity);
        mContext = activity;
    }

//        dialog.show(this, "Showing Data..", "please wait", true, false);

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
//            callApiAuth(strings[0]);
            callApiAuth();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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

    public void callApiAuth() throws IOException {
        URL url = null;
        try {
            url = new URL("https://gateway.chocodev.kz/auth/token");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("client_id", "12697011");
        params.put("client_secret", "7k4fv685jckshj5wljplsot707olo2x9sn58uba6l8djhomu4sk92y5xaurd66q8");
        params.put("grant_type", "client_credentials");

        Set set = params.entrySet();
        Iterator i = set.iterator();
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
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

        JsonElement root = new JsonParser().parse(builder.toString());
        token = root.getAsJsonObject().get("data").getAsJsonObject().get("token").getAsString();

        Map<String,Object> data = new ObjectMapper()
                .readValue(root.getAsJsonObject().get("data").getAsJsonObject().toString(), HashMap.class);


        for (Map.Entry<String, Object> entry : data.entrySet()) {
            System.out.println("!! AAAA" + entry.getKey() + ":" + entry.getValue());
        }

        System.out.println("Server output returned: "+ builder.toString());
        System.out.println("Server token returned: "+ token);

    }

    public JsonElement readAPIOtput(String builder) throws IOException {
        JsonElement root = new JsonParser().parse(builder);
        return root;
    }

    public Map<String,Object> readAPIData(JsonElement root) throws IOException {
        Map<String,Object> data = new ObjectMapper()
                .readValue(root.getAsJsonObject().get("data").getAsJsonObject().toString(), HashMap.class);
//        for (Map.Entry<String, Object> entry : data.entrySet()) {
//            System.out.println("!! AAAA" + entry.getKey() + ":" + entry.getValue());
//        }
        return data;
    }
}



