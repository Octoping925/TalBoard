package com.talmo.talboard.exception;

import com.talmo.talboard.config.ExceptionConstants;

public class PostReportException extends RuntimeException{
    public PostReportException() {
        super(ExceptionConstants.ALREADY_REPORT_POST_MESSAGE);
    }
}
