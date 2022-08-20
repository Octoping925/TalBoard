package com.talmo.talboard.exception;

public class NoMemberFoundException extends RuntimeException  {
    public NoMemberFoundException() {
        super("회원 정보 찾지 못함");
    }

    public NoMemberFoundException(String data) {
        super("회원 정보 찾지 못함");
    }
}
