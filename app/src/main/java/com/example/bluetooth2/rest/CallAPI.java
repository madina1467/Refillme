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

    public String company;

    public CallAPI(String company){
        this.company = company;
    }

    public void callSenimCreateAPI(Qrcode activity, ImageView imageView){
        new PostMethod(activity, imageView).execute("create", this.company);
    }

    public void callRahmetCreateAPI(Qrcode activity, ImageView imageView){

        //TODO
//        new PostMethod(activity).execute("check", this.action);

        new PostMethod(activity).execute("auth", this.company);
        new PostMethod(activity).execute("create", this.company);
    }

    public void callSenimStatusAPI(Qrcode activity, ImageView imageView){
        //todo
        new PostMethod(activity).execute("status", this.company);
    }

    public void callRahmetStatusAPI(Qrcode activity, ImageView imageView){
        new PostMethod(activity).execute("status", this.company);
    }

    public void callSenimAPI(Qrcode activity, ImageView imageView){
        //TODO
    }


    public static Map<String,Object> getAPIResult(String message, String comp) throws IOException {
        JsonElement root = readAPIOtput(message.toString());

        if("senim".equals(comp)) {
            return readSenimAPIData(root);
        } else if("rahmet".equals(comp)){
            return readAPIData(root);
        } else {
            System.err.println("COMPANY TO READ API NOT SHOWN");
            return readAPIData(root);
//            throw new IOException("COMPANY TO READ API NOT SHOWN");
        }
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
        Map<String, Map<String, Object>> map = new ObjectMapper()
                .readValue(root.getAsJsonObject().get("content").getAsJsonObject().toString(), HashMap.class);;
        return map.get("data");
    }

}