package com.talmo.talboard.config;

import lombok.Builder;
import lombok.Data;

@Builder
public class ResponseObject {
    String message;
    Object data;

    public ResponseObject(Object data) {
        this.data = data;
    }

    public ResponseObject(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
