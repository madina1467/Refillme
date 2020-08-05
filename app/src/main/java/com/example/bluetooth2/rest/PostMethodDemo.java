package com.example.bluetooth2.rest;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class PostMethodDemo extends AsyncTask<String, Void, String> {
    String server_response;

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            testCall(strings[0]);
//            url = new URL(strings[0]);
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setDoOutput(true);
//            urlConnection.setDoInput(true);
//            urlConnection.setRequestMethod("POST");
//            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; UTF-8");
////            urlConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; UTF-8");
//
//
//            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
//
////            String jsonInputString = "{\"client_id\": \"12697011\",\"client_secret\":\"7k4fv685jckshj5wljplsot707olo2x9sn58uba6l8djhomu4sk92y5xaurd66q8\",\"grant_type\":\"client_credentials\"}"; //data to post
//            try {
//
//                JSONObject obj = new JSONObject();
//                obj.put("client_id", "12697011");
//                obj.put("client_secret", "7k4fv685jckshj5wljplsot707olo2x9sn58uba6l8djhomu4sk92y5xaurd66q8");
//                obj.put("grant_type", "client_credentials");
//
//                wr.writeBytes(obj.toString());
//                Log.e("JSON Input", obj.toString());
//                wr.flush();
//                wr.close();
//            }
//            catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            urlConnection.connect();
//
//            int responseCode = urlConnection.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                server_response = readStream(urlConnection.getInputStream());
//            }
//
////            int code = urlConnection.getResponseCode();
////            System.out.println(code);
////
////            try(BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"))){
////                StringBuilder response = new StringBuilder();
////                String responseLine = null;
////                while ((responseLine = br.readLine()) != null) {
////                    response.append(responseLine.trim());
////                }
////                System.out.println(response.toString());
////            }
//
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
    }


    public static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    public static void testCall(String httpurl) throws IOException {
        URL url = null;
        try {
            url = new URL(httpurl);
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
        System.out.println("INSTAGRAM token returned: "+builder.toString());
    }

}


