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

    String testId = "ididid";
    String testPw = "pwpwpw";
    String testEmail = "test@test.com";

    @Test
    public void join() {
        // given
        Member member = new Member(testId, testPw, testEmail);

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
        Member member1 = new Member(testId, testPw, testEmail);
        Member member2 = new Member(testId, testPw, testEmail + "aaa");

        // when
        memberService.join(member1);

        // then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 아이디 또는 이메일", thrown.getMessage());
    }

    @Test
    public void resign() {
        // given
        Member member = new Member(testId + "1", testPw, testEmail);
        memberRepository.save(member);
        Member member2 = new Member(testId + "2", testPw, testEmail + "1");
        member2.setAdminYn(true);
        memberRepository.save(member2);
        Member member3 = new Member(testId + "3", testPw, testEmail + "2");
        memberRepository.save(member3);

        // when
        memberService.resign(member.getId(), member.getId());
        memberService.resign(member2.getId(), member3.getId());

        // then
        assertTrue(member.isResignYn());
        assertTrue(member3.isResignYn());
    }

    @Test
    public void findId() {
        // given
        Member member = new Member(testId, testPw, testEmail);
        memberService.join(member);

        // when
        String findId = memberService.findId(member.getEmailAddress());

        // then
        assertEquals(member.getId(), findId);
    }

    @Test
    public void findId_실패() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.findId("cant@exist.com"));
        assertEquals("회원 정보 찾지 못함", thrown.getMessage());
    }

    @Test
    public void findPassword() {
        // given
        Member member = new Member(testId, testPw, testEmail);
        memberService.join(member);

        // when
        String findPassword = memberService.findPassword(member.getId());

        // then
        assertEquals(member.getPassword(), findPassword);
    }

    @Test
    public void findPassword_실패() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.findPassword("cantexist"));
        assertEquals("회원 정보 찾지 못함", thrown.getMessage());
    }
}