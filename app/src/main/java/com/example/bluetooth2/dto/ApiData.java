package com.example.bluetooth2.dto;

import com.example.bluetooth2.dao.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ApiData {

    public static String token;
    protected String action;

    public ApiData(String action) {
        this.action = action;
    }

    public static void setRahmetToken(String AuthMessage) throws IOException {
        JsonElement root = new JsonParser().parse(AuthMessage);
        Map<String,Object> data = new ObjectMapper()
                .readValue(root.getAsJsonObject().get("data").getAsJsonObject().toString(), HashMap.class);
        ApiData.token = (String) data.get("token");
    }

    public abstract HashMap<String, String> getParams(Order order);

    public abstract String getURL() throws IOException;

    public abstract String getRequestMethod() throws IOException;

}
