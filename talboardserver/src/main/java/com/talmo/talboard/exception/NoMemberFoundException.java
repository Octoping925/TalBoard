package com.talmo.talboard.exception;

import com.talmo.talboard.config.ExceptionConstants;

public class NoMemberFoundException extends RuntimeException  {
    public NoMemberFoundException() {
        super(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE);
    }
}
