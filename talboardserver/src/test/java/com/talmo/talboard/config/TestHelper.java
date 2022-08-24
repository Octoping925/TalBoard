package com.talmo.talboard.config;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import com.talmo.talboard.domain.vo.PostCreateVO;

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

    public static String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";

    public static String getLoremIpsum(int length) {
        return loremIpsum.substring(0, length);
    }

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

    public static Post createPost(Member member) {
        PostCreateVO vo = new PostCreateVO(getLoremIpsum(10), getLoremIpsum(10));
        return Post.create(vo, member);
    }

    public static Post createPost(Member member, int titleLength, int contextLength) {
        PostCreateVO vo = new PostCreateVO(getLoremIpsum(titleLength), getLoremIpsum(contextLength));
        return Post.create(vo, member);
    }
}
