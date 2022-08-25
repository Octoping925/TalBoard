package com.talmo.talboard.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.exception.ExceptionConstants;
import org.junit.jupiter.api.Test;

public class MemberTest {
    @Test
    public void changePassword() {
        // given
        Member member = TestHelper.createMember();

        // when
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> member.changePassword(
            TestHelper.failPw));
        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> member.changePassword(
            TestHelper.failPw2));
        member.changePassword(TestHelper.testPw2);

        // then
        assertEquals(ExceptionConstants.INVALID_PW_MESSAGE, thrown.getMessage());
        assertEquals(ExceptionConstants.INVALID_PW_MESSAGE, thrown2.getMessage());
        assertEquals(TestHelper.testPw2, member.getPassword());
    }

    @Test
    public void changeEmailAddress() {
        // given
        Member member = TestHelper.createMember();

        // when
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> member.changeEmailAddress(TestHelper.failEmail));
        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> member.changeEmailAddress(TestHelper.failEmail2));
        IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class, () -> member.changeEmailAddress(TestHelper.failEmail3));
        member.changeEmailAddress(TestHelper.testEmail2);

        // then
        assertEquals(ExceptionConstants.INVALID_EMAIL_MESSAGE, thrown.getMessage());
        assertEquals(ExceptionConstants.INVALID_EMAIL_MESSAGE, thrown2.getMessage());
        assertEquals(ExceptionConstants.INVALID_EMAIL_MESSAGE, thrown3.getMessage());

        assertEquals(TestHelper.testEmail2, member.getEmailAddress());
    }
}