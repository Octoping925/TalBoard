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
    @Autowired MemberRepository memberRepository;

    @Test
    public void join() {
        // given
        Member member = new Member("id", "pw", "test@naver.com");

        // when
        Long member_no = memberService.join(member);

        // then
        Member findMember = memberRepository.findOne(member_no);
        assertEquals(member.getId(), findMember.getId());
        assertEquals(member.getPassword(), findMember.getPassword());
        assertEquals(member.getEmailAddress(), findMember.getEmailAddress());
    }

    @Test
    public void join_중복체크() {
        // given
        Member member1 = new Member("test", "123", "test@naver.com");
        Member member2 = new Member("test", "123", "talmo@naver.com");

        // when
        memberService.join(member1);

        // then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다", thrown.getMessage());
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
        Member member = new Member("id", "pw", "test@naver.com");
        memberService.join(member);

        // when
        String findId = memberService.findId(member.getEmailAddress());

        // then
        assertEquals(member.getId(), findId);
    }

    @Test
    public void findId_실패() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.findId("cant@exist.com"));
        assertEquals("존재하지 않는 이메일 주소입니다", thrown.getMessage());
    }

    @Test
    public void findPassword() {
        // given
        Member member = new Member("id", "pw", "test@naver.com");
        memberService.join(member);

        // when
        String findPassword = memberService.findPassword(member.getId());

        // then
        assertEquals(member.getPassword(), findPassword);
    }

    @Test
    public void findPassword_실패() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.findPassword("cantexist"));
        assertEquals("존재하지 않는 아이디입니다", thrown.getMessage());
    }
}