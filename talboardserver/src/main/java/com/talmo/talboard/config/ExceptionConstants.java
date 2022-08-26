package com.talmo.talboard.config;

public class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Utility Class");
    }

    public static final String INVALID_ID_MESSAGE = "아이디 유효성 검사 실패";
    public static final String INVALID_PW_MESSAGE = "비밀번호 유효성 검사 실패";
    public static final String INVALID_EMAIL_MESSAGE = "이메일 유효성 검사 실패";

    public static final String NO_MEMBER_FOUND_MESSAGE = "회원 정보 찾지 못함";
    
    public static final String DUPLICATE_ID_MESSAGE = "이미 존재하는 아이디";
    public static final String DUPLICATE_EMAIL_MESSAGE = "이미 존재하는 이메일";

    public static final String NO_AUTHORIZE_MESSAGE = "권한 없음";


}
