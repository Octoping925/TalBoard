package com.talmo.talboard.config;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.vo.MemberJoinVO;

public class TestHelper {
    public static String testId = "ididid";
    public static String testId2 = "ididid2";
    public static String testPw = "pwpwpw";
    public static String testPw2 = "pwpwpw2";
    public static String testEmail = "test@test.com";
    public static String testEmail2 = "test2@test.com";
    public static String testEmail3 = "test3@test.com";

    public static String failPw = "pw";
    public static String failPw2 = "pwpwpw pw";
    public static String failEmail = "test";
    public static String failEmail2 = "test@";
    public static String failEmail3 = "@test.com";

    public static Member createMember() {
        MemberJoinVO vo = new MemberJoinVO(testId, testPw, testEmail);
        return Member.regist(vo);
    }
    public static Member createMember(int number) {
        MemberJoinVO vo = new MemberJoinVO(testId + number, testPw + number, testEmail + number);
        return Member.regist(vo);
    }
    public static Member createMember(String id, String password, String emailAddress) {
        MemberJoinVO vo = new MemberJoinVO(id, password, emailAddress);
        return Member.regist(vo);
    }
}
