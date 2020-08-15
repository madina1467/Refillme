package com.example.bluetooth2.rest;

import android.widget.ImageView;

import com.example.bluetooth2.Qrcode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CallAPI {

    public CallAPI(){
    }

    public void callAuthAPI(Qrcode activity, ImageView imageView){
        new PostMethod(activity, imageView).execute("create", "senim");
//        new PostMethod(activity).execute("auth", "rahmet");
    }

    public void callCheckApi(Qrcode activity){
        new PostMethod(activity).execute("check", "rahmet");
    }

    public void callCreateApi(Qrcode activity){
        new PostMethod(activity).execute("create", "rahmet");
    }

    public void callStatusApi(Qrcode activity){
        new PostMethod(activity).execute("status", "rahmet");
    }

    public static Map<String,Object> getAPIResult(String message) throws IOException {
        JsonElement root = readAPIOtput(message.toString());
        return readSenimAPIData(root);
    }

    public static JsonElement readAPIOtput(String builder) throws IOException {
        JsonElement root = new JsonParser().parse(builder);
        return root;
    }

    public static Map<String,Object> readAPIData(JsonElement root) throws IOException {
        Map<String,Object> data = new ObjectMapper()
                .readValue(root.getAsJsonObject().get("data").getAsJsonObject().toString(), HashMap.class);
//        for (Map.Entry<String, Object> entry : data.entrySet()) {
//            System.out.println("!! AAAA" + entry.getKey() + ":" + entry.getValue());
//        }
        return data;
    }

    public static Map<String,Object> readSenimAPIData(JsonElement root) throws IOException {
//        Map<String,Object> content = new ObjectMapper()
//                .readValue(root.getAsJsonObject().get("content").getAsJsonObject().toString(), HashMap.class);
//        Map<String,Object> data = new ObjectMapper()
//                .readValue(content.get("data").toString(), HashMap.class);

//        Map<String, Map<String, String>> map =
//                new HashMap<String, Map<String, String>>();

        Map<String, Map<String, Object>> map = new ObjectMapper()
                .readValue(root.getAsJsonObject().get("content").getAsJsonObject().toString(), HashMap.class);;

        return map.get("data");
    }

}