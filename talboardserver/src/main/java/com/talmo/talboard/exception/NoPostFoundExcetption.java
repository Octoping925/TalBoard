package com.talmo.talboard.exception;

import com.talmo.talboard.config.ExceptionConstants;

public class NoPostFoundExcetption extends RuntimeException{
    public NoPostFoundExcetption() {
        super(ExceptionConstants.NOT_FOUND_POST_MESSAGE);
    }
}
