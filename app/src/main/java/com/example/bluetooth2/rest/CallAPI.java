package com.example.bluetooth2.rest;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallAPI extends AsyncTask<String, String, String> {

        public CallAPI(){
            //set context variables if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

         @Override
         protected String doInBackground(String... params) {
            String urlString = params[0]; // URL to call
//            String data = params[1]; //data to post
            String data = "{\"client_id\": \"12697011\",\"client_secret\":\"7k4fv685jckshj5wljplsot707olo2x9sn58uba6l8djhomu4sk92y5xaurd66q8\",\"grant_type\":\"client_credentials\"}"; //data to post

             OutputStream out = null;
            String response = "";

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                out = new BufferedOutputStream(urlConnection.getOutputStream());


                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(data);
                writer.flush();
                writer.close();
                out.close();

                urlConnection.connect();

                int responseCode=urlConnection.getResponseCode();

//                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
//                }
//                else {
//                    response="!!!!! EREEERREE ERROR ERROR";
//
//                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
             System.err.println("!!!!!!!!!!!! Output: " + response);
             return response;
         }
    }