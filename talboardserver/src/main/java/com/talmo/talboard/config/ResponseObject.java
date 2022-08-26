package com.talmo.talboard.config;

import java.util.HashMap;
import java.util.Map;

public class ResponseObject {
    private ResponseObject() {
        throw new IllegalStateException("Utility Class");
    }

    public static Map<String, Object> create(Object data, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("message", message);
        return res;
    }
}
