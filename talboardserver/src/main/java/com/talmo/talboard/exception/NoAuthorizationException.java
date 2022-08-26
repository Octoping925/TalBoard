package com.talmo.talboard.exception;

import com.talmo.talboard.config.ExceptionConstants;

public class NoAuthorizationException extends RuntimeException {
    public NoAuthorizationException() {
        super(ExceptionConstants.NO_AUTHORIZE_MESSAGE);
    }
}
