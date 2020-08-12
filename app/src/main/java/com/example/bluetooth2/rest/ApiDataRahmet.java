package com.example.bluetooth2.rest;

import java.io.IOException;
import java.util.HashMap;

public class ApiDataRahmet extends ApiData{

    public static String token;

    public ApiDataRahmet(String action) {
        super(action);
    }

    public HashMap<String, String> getParams(){
        HashMap<String, String> params = new HashMap<String, String>();

        switch (this.action){
            case "check":
//                params.put("token", token);
                break;
            case "auth":
                params.put("client_id", "12697011");
                params.put("client_secret", "7k4fv685jckshj5wljplsot707olo2x9sn58uba6l8djhomu4sk92y5xaurd66q8");
                params.put("grant_type", "client_credentials");
                break;
            case "create":
                params.put("merchant_order_id", "123543");
                params.put("amount", "1000");
                params.put("token", "baeb767cc64f213a2e54fd2ba436d80c");
                break;
            case "status":
                params.put("merchant_order_ids[]", "123543");
                break;
//            case "refund":
//                break;
        }
        return params;
    }

    public String getURL() throws IOException {
        switch (this.action){
            case "check":
                return "https://gateway.chocodev.kz/orders/v1/preorder/availability";
            case "auth":
                return "https://gateway.chocodev.kz/auth/token";
            case "create":
                return "https://gateway.chocodev.kz/orders/v1/preorder/create";
            case "status":
                return "https://gateway.chocodev.kz/orders/v1/preorder/status";
            case "refund":
                return "https://gateway.chocodev.kz/orders/v1/preorder/refund";
        }
        throw new IOException("Not such action for Rahmet URL");
    }

    public String getRequestMethod() throws IOException {
        if("check".equals(this.action))
            return "GET";
        else
            return "POST";
    }

}
