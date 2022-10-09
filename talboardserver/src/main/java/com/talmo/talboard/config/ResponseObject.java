package com.talmo.talboard.config;

import lombok.Data;

@Data
public class ResponseObject {

    Object data;
    String message;

    private ResponseObject(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    public static ResponseObject create(Object data, String message) {
        return new ResponseObject(data, message);
    }
}
