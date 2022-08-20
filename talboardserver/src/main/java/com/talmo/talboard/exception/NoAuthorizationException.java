package com.talmo.talboard.exception;

public class NoAuthorizationException extends RuntimeException {
    public NoAuthorizationException() {

    }

    public NoAuthorizationException(String type) {
        super(type + "권한 없음");
    }
}
