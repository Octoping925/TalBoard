package com.talmo.talboard.exception;

public class NoMemberFoundException extends RuntimeException  {
    public NoMemberFoundException() {
        super(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE);
    }
}
