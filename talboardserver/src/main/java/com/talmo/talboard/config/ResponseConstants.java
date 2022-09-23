package com.talmo.talboard.config;

public class ResponseConstants {
    private ResponseConstants() {
        throw new IllegalStateException("Utility Class");
    }

    public static final String SEARCH_SUCCESS_MESSAGE = "조회 성공";
    public static final String CHANGE_SUCCESS_MESSAGE = "변경 성공";
    public static final String REGIST_SUCCESS_MESSAGE = "회원 가입 성공";
    public static final String RESIGN_SUCCESS_MESSAGE = "회원 탈퇴 성공";

    public static final String FINDID_SUCCESS_MESSAGE = "아이디 찾기 성공";
    public static final String FINDPW_SUCCESS_MESSAGE = "비밀번호 찾기 성공";

    public static final String BLOCK_SUCCESS_MESSAGE = "차단 성공";
    public static final String UNBLOCK_SUCCESS_MESSAGE = "차단 해제 성공";

    public static final String REPORT_SUCCESS_MESSAGE = "게시글 신고 성공";
    public static final String UNREPORT_SUCCESS_MESSAGE = "게시글 신고 취소 성공";





}
