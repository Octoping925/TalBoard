package com.talmo.talboard.exception;

import com.talmo.talboard.config.ExceptionConstants;

public class NoNoticeFoundException  extends RuntimeException  {
    public NoNoticeFoundException() {
        super(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE);
    }
}
