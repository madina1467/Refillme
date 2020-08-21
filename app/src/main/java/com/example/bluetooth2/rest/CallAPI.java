package com.example.bluetooth2.rest;

import android.widget.ImageView;

import com.example.bluetooth2.Qrcode;
import com.example.bluetooth2.dao.Order;
import com.example.bluetooth2.database.FirebaseDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CallAPI {

    private Order order;
    private FirebaseDB fb;

    public CallAPI(Order order){
        this.order = order;
        this.fb = FirebaseDB.getInstance();
    }

    public void callCreateAPI(Qrcode activity, ImageView imageView){
        fb.addOrder(order);
        if("senim".equals(this.order.getPaidByCompany())) {
            new PostMethod(activity, order, imageView).execute("create");
        } else if("rahmet".equals(this.order.getPaidByCompany())){
            //TODO
            //new PostMethod(activity).execute("check", this.action);
            new PostMethod(activity, order).execute("auth");
            new PostMethod(activity, order, imageView).execute("create");
        } else {
            System.err.println("COMPANY TO READ API NOT SHOWN");
//            return readAPIData(root);
//            throw new IOException("COMPANY TO READ API NOT SHOWN");
        }
    }

    public void callSenimStatusAPI(Qrcode activity){
        new PostMethod(activity, order).execute("status");
    }

    public void callRahmetStatusAPI(Qrcode activity){
        new PostMethod(activity, order).execute("status");
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
        if(root != null){
            Map<String,Object> data = new ObjectMapper()
                    .readValue(root.getAsJsonObject().get("data").getAsJsonObject().toString(), HashMap.class);
            return data;
        }
        throw new IOException("Error root is NULL");
    }

    public static Map<String,Object> readSenimAPIData(JsonElement root) throws IOException {
        Map<String, Map<String, Object>> map = new ObjectMapper()
                .readValue(root.getAsJsonObject().get("content").getAsJsonObject().toString(), HashMap.class);;
        return map.get("data");
    }

    public static Map<String,Object> readAPIOtputResult(String builder) throws IOException {
        JsonElement root = new JsonParser().parse(builder);
        Map<String,Object> data = new ObjectMapper()
                .readValue(root.getAsJsonObject().toString(), HashMap.class);
//        for (Map.Entry<String, Object> entry : data.entrySet()) {
//            System.out.println("!! BBB" + entry.getKey() + ":" + entry.getValue());
//        }
        return data;
    }

}