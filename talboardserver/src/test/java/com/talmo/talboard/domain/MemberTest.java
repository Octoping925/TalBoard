package com.talmo.talboard.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.config.TestHelper;
import org.junit.jupiter.api.Test;

public class MemberTest {
    @Test
    public void changePassword() {
        // given
        Member member = TestHelper.createTestMember();

        // when
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> member.changePassword(
            TestHelper.failPw));
        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> member.changePassword(
            TestHelper.failPw2));
        member.changePassword(TestHelper.testPw2);

        // then
        assertEquals("비밀번호 유효성 검사 실패", thrown.getMessage());
        assertEquals("비밀번호 유효성 검사 실패", thrown2.getMessage());
        assertEquals(TestHelper.testPw2, member.getPassword());
    }

    @Test
    public void changeEmailAddress() {
        // given
        Member member = TestHelper.createTestMember();

        // when
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> member.changeEmailAddress(TestHelper.failEmail));
        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> member.changeEmailAddress(TestHelper.failEmail2));
        IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class, () -> member.changeEmailAddress(TestHelper.failEmail3));
        member.changeEmailAddress(TestHelper.testEmail2);

        // then
        assertEquals("이메일 유효성 검사 실패", thrown.getMessage());
        assertEquals("이메일 유효성 검사 실패", thrown2.getMessage());
        assertEquals("이메일 유효성 검사 실패", thrown3.getMessage());

        assertEquals(TestHelper.testEmail2, member.getEmailAddress());
    }
}