package com.talmo.talboard.exception;

public class NoAuthorizationException extends RuntimeException {
    public NoAuthorizationException() {
        super(ExceptionConstants.NO_AUTHORIZE_MESSAGE);
    }
}
