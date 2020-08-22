package com.example.bluetooth2.dto;

import com.example.bluetooth2.dao.Order;

import java.io.IOException;
import java.util.HashMap;

public class ApiDataSenim extends ApiData {

    public ApiDataSenim(String action) {
        super(action);
    }

    public HashMap<String, String> getParams(Order order){
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
                params.put("branchId", String.valueOf(order.getBranchId()));
                params.put("dimension", "200");
                params.put("duration", "10000");
                params.put("format", "1");
                params.put("orderAmount", String.valueOf(order.getPrice()));
                params.put("orderId", order.getOrderId().toString());
                break;
            case "status":
                params.put("orderId", order.getOrderId().toString());
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
