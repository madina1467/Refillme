package com.example.bluetooth2.rest;

import java.io.IOException;
import java.util.HashMap;

public class ApiDataSenim extends ApiData {

    public ApiDataSenim(String action) {
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
                params.put("branchId", "3474");
                params.put("dimension", "200");
                params.put("duration", "10000");
                params.put("format", "1");
                params.put("orderAmount", "12332");
                params.put("orderId", "11");
                break;
            case "status":
                params.put("orderId", "11");
                break;
//            case "refund":
//                break;
        }
        return params;
    }

    public String getURL() throws IOException {
        switch (this.action){
            case "check":
                return "";
            case "auth":
                return "";
            case "create":
                return "https://test.senim.kz/api/integration/v2/qr";
            case "status":
                return "https://test.senim.kz/api/integration/v2/orders/status";
            case "refund":
                return "htt";
        }
        throw new IOException("Not such action for Rahmet URL");
    }

    public String getRequestMethod() throws IOException {
        return "POST";
    }

}
