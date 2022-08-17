package com.talmo.talboard.service;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;

    @Test
    public void join() {
    }

    @Test
    public void resign() {
        // given
        Member member = new Member("testid", "testpw", "test@naver.com");
        Long member_no = memberService.join(member);

        // when
        memberService.resign(member_no);

        // then
        assertTrue(member.isResignYn());
    }

    @Test
    public void findId() {
        // given
        String id = "testpw";
        Member member = new Member(id, "testpw", "test@naver.com");
        memberService.join(member);

        // when
        String findId = memberService.findId(member.getEmailAddress());

        // then
        assertEquals(id, findId);
    }

    @Test
    public void findPassword() {
        // given
        String pw = "testpw";
        Member member = new Member("testid", pw, "test@naver.com");
        memberService.join(member);

        // when
        String findPassword = memberService.findPassword(member.getId());

        // then
        assertEquals(pw, findPassword);
    }
}