package com.talmo.talboard.service;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.vo.MemberDataChangeVO;
import com.talmo.talboard.exception.NoAuthorizationException;
import com.talmo.talboard.exception.NoMemberFoundException;
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
    String testId2 = "ididid2";
    String testPw = "pwpwpw";
    String testPw2 = "pwpwpw2";
    String testEmail = "test@test.com";
    String testEmail2 = "test2@test.com";

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
        Member member2 = new Member(testId, testPw2, testEmail2);

        // when
        memberService.join(member1);

        // then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 아이디 또는 이메일", thrown.getMessage());
    }

    @Test
    public void resign() {
        // given
        Member member = new Member(testId, testPw, testEmail);
        Member member2 = new Member(testId2, testPw, testEmail + "1");
        member.setAdminYn(true);

        memberRepository.save(member);
        memberRepository.save(member2);

        // when
        memberService.resign(member.getId(), member2.getId());
        memberService.resign(member.getId(), member.getId());

        // then
        assertTrue(member.isResignYn());
        assertTrue(member2.isResignYn());
    }

    @Test
    public void resign_실패() {
        // given
        Member member = new Member(testId, testPw, testEmail);
        Member member2 = new Member(testId2, testPw, testEmail2);

        memberRepository.save(member);
        memberRepository.save(member2);

        // when
        NoAuthorizationException thrown = assertThrows(NoAuthorizationException.class, () -> memberService.resign(member.getId(), member2.getId()));
        NoMemberFoundException thrown2 = assertThrows(NoMemberFoundException.class, () -> memberService.resign(member.getId(), "cantExist"));

        // then
        assertEquals("회원 탈퇴권한 없음", thrown.getMessage());
        assertEquals("회원 정보 찾지 못함", thrown2.getMessage());
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
        NoMemberFoundException thrown = assertThrows(NoMemberFoundException.class, () -> memberService.findId("cant@exist.com"));
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
        NoMemberFoundException thrown = assertThrows(NoMemberFoundException.class, () -> memberService.findPassword("cantexist"));
        assertEquals("회원 정보 찾지 못함", thrown.getMessage());
    }

    @Test
    public void updateMemberData() {
        // given
        Member member = new Member(testId, testPw, testEmail);
        memberRepository.save(member);

        MemberDataChangeVO vo = new MemberDataChangeVO();
        vo.setId(testId);
        vo.setPassword(testPw2);

        // when
        memberService.updateMemberData(vo);

        // then
        assertEquals(testPw2, memberRepository.findOneActualMemberById(testId).getPassword());
    }

    @Test
    public void updateMemberData_실패() {
        // given
        memberRepository.save(new Member(testId, testPw, testEmail));

        MemberDataChangeVO vo = new MemberDataChangeVO();
        MemberDataChangeVO vo2 = new MemberDataChangeVO();
        vo.setId(testId);
        vo.setEmailAddress(testEmail);

        vo2.setId(testId2);

        // when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.updateMemberData(vo));
        NoMemberFoundException thrown2 = assertThrows(NoMemberFoundException.class, () -> memberService.updateMemberData(vo2));

        // then
        assertEquals("이미 존재하는 이메일", thrown.getMessage());
        assertEquals("회원 정보 찾지 못함", thrown2.getMessage());
    }
}