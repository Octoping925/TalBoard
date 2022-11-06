package com.talmo.talboard.service;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.vo.MemberDataChangeVO;
import com.talmo.talboard.config.ExceptionConstants;
import com.talmo.talboard.exception.NoAuthorizationException;
import com.talmo.talboard.exception.NoMemberFoundException;
import com.talmo.talboard.repository.MemberRepository;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    BlockService blockService;

    Member member1, member2;

    @BeforeEach
    void beforeEach() {
        member1 = TestHelper.createMember(1);
        member2 = TestHelper.createMember(2);
        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @Test
    void join() {
        // given
        Member member = TestHelper.createMember();

        // when
        Long memberNo = memberService.join(member);

        // then
        Member findMember = memberRepository.findOne(memberNo);
        assertEquals(member.getId(), findMember.getId());
        assertEquals(member.getPassword(), findMember.getPassword());
        assertEquals(member.getEmailAddress(), findMember.getEmailAddress());
    }

    @Test
    void join_중복체크() {
        // given
        Member member1 = TestHelper.createMember(TestHelper.testId, TestHelper.testPw, TestHelper.testEmail);
        Member member2 = TestHelper.createMember(TestHelper.testId, TestHelper.testPw2, TestHelper.testEmail2);

        // when
        memberService.join(member1);

        // then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals(ExceptionConstants.DUPLICATE_ID_MESSAGE, thrown.getMessage());
    }

    @Test
    void resign() {
        // given
        member1.setAdminYn(true);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        memberService.resign(member1, member2);
        memberService.resign(member1, member1);

        // then
        assertTrue(member1.isResignYn());
        assertTrue(member2.isResignYn());
    }

    @Test
    void resign_실패() {
        // given
        // when
        NoAuthorizationException thrown = assertThrows(NoAuthorizationException.class, () -> memberService.resign(member1, member2));

        // then
        assertEquals(ExceptionConstants.NO_AUTHORIZE_MESSAGE, thrown.getMessage());
    }

    @Test
    void findId() {
        // given
        // when
        String findId = memberService.findId(member1.getEmailAddress());

        // then
        assertEquals(member1.getId(), findId);
    }

    @Test
    void findId_실패() {
        NoMemberFoundException thrown = assertThrows(NoMemberFoundException.class, () -> memberService.findId("cant@exist.com"));
        assertEquals(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, thrown.getMessage());
    }

    @Test
    void findPassword() {
        // given
        // when
        String findPassword = memberService.findPassword(member1.getId());

        // then
        assertEquals(member1.getPassword(), findPassword);
    }

    @Test
    void findPassword_실패() {
        NoMemberFoundException thrown = assertThrows(NoMemberFoundException.class, () -> memberService.findPassword("cantexist"));
        assertEquals(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, thrown.getMessage());
    }

    @Test
    void updateMemberData() {
        // given
        MemberDataChangeVO vo = new MemberDataChangeVO();
        vo.setMemberNo(member1.getMemberNo());
        vo.setPassword(TestHelper.testPw2);

        // when
        memberService.updateMemberData(member1, vo);

        // then
        assertEquals(TestHelper.testPw2, memberRepository.findOne(member1.getMemberNo()).getPassword());
    }

    @Test
    void updateMemberData_실패() {
        // given
        MemberDataChangeVO vo = new MemberDataChangeVO();
        vo.setMemberNo(member1.getMemberNo());
        vo.setEmailAddress(member1.getEmailAddress());

        // when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.updateMemberData(member1, vo));

        // then
        assertEquals(ExceptionConstants.DUPLICATE_EMAIL_MESSAGE, thrown.getMessage());
    }
}