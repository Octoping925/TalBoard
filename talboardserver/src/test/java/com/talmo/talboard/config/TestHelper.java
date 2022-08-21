package com.talmo.talboard.config;

import com.talmo.talboard.domain.Member;

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

    public static Member createTestMember() {
        return new Member(testId, testPw, testEmail);
    }
    public static Member createTestMember(int number) {
        return new Member(testId + number, testPw + number, testEmail + number);
    }
}
