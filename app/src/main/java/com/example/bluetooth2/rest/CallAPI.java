package com.example.bluetooth2.rest;

import com.example.bluetooth2.MainActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CallAPI {

    public CallAPI(){
    }

    public void callAuthAPI(MainActivity activity){
        new PostMethod(activity).execute("auth");
    }

    public void callCheckApi(MainActivity activity){
        new PostMethod(activity).execute("check");
    }

    public void callCreateApi(MainActivity activity){
        new PostMethod(activity).execute("create");
    }

    public void callStatusApi(MainActivity activity){
        new PostMethod(activity).execute("status");
    }

    public Map<String,Object> getAPIResult(String message) throws IOException {
        JsonElement root = readAPIOtput(message.toString());
        return readAPIData(root);
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