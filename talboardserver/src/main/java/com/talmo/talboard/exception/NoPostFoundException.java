package com.talmo.talboard.exception;

import com.talmo.talboard.config.ExceptionConstants;

public class NoPostFoundException extends RuntimeException{
    public NoPostFoundException() {
        super(ExceptionConstants.NOT_FOUND_POST_MESSAGE);
    }
}
