package com.example.bluetooth2.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ApiData {

    protected static String token;
    protected String action;

    public ApiData(String action) {
        this.action = action;
    }

    public static void setToken(String AuthMessage) throws IOException {
        JsonElement root = new JsonParser().parse(AuthMessage);
        Map<String,Object> data = new ObjectMapper()
                .readValue(root.getAsJsonObject().get("data").getAsJsonObject().toString(), HashMap.class);
        ApiData.token = (String) data.get("token");
    }

    abstract HashMap<String, String> getParams();

    abstract String getURL() throws IOException;

    abstract String getRequestMethod() throws IOException;

}
