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
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        member.setAdminYn(true);

        memberRepository.save(member);
        memberRepository.save(member2);

        // when
        memberService.resign(member, member2);
        memberService.resign(member, member);

        // then
        assertTrue(member.isResignYn());
        assertTrue(member2.isResignYn());
    }

    @Test
    void resign_실패() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);

        memberRepository.save(member);
        memberRepository.save(member2);

        // when
        NoAuthorizationException thrown = assertThrows(NoAuthorizationException.class, () -> memberService.resign(member, member2));

        // then
        assertEquals(ExceptionConstants.NO_AUTHORIZE_MESSAGE, thrown.getMessage());
    }

    @Test
    void findId() {
        // given
        Member member = TestHelper.createMember();
        memberService.join(member);

        // when
        String findId = memberService.findId(member.getEmailAddress());

        // then
        assertEquals(member.getId(), findId);
    }

    @Test
    void findId_실패() {
        NoMemberFoundException thrown = assertThrows(NoMemberFoundException.class, () -> memberService.findId("cant@exist.com"));
        assertEquals(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, thrown.getMessage());
    }

    @Test
    void findPassword() {
        // given
        Member member = TestHelper.createMember();
        memberService.join(member);

        // when
        String findPassword = memberService.findPassword(member.getId());

        // then
        assertEquals(member.getPassword(), findPassword);
    }

    @Test
    void findPassword_실패() {
        NoMemberFoundException thrown = assertThrows(NoMemberFoundException.class, () -> memberService.findPassword("cantexist"));
        assertEquals(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, thrown.getMessage());
    }

    @Test
    void updateMemberData() {
        // given
        Member member = TestHelper.createMember();
        memberRepository.save(member);

        MemberDataChangeVO vo = new MemberDataChangeVO();
        vo.setMemberNo(member.getMemberNo());
        vo.setPassword(TestHelper.testPw2);

        // when
        memberService.updateMemberData(member, vo);

        // then
        assertEquals(TestHelper.testPw2, memberRepository.findOne(member.getMemberNo()).getPassword());
    }

    @Test
    void updateMemberData_실패() {
        // given
        Member member = TestHelper.createMember();
        memberRepository.save(member);

        MemberDataChangeVO vo = new MemberDataChangeVO();
        vo.setMemberNo(member.getMemberNo());
        vo.setEmailAddress(member.getEmailAddress());

        // when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.updateMemberData(member, vo));

        // then
        assertEquals(ExceptionConstants.DUPLICATE_EMAIL_MESSAGE, thrown.getMessage());
    }

    @Test
    void findMemberBlockList() {
        // given
        Member[] members = new Member[3];
        for(int i = 0; i < 3; ++i) {
            members[i] = TestHelper.createMember(i);
            memberService.join(members[i]);
        }
        blockService.blockMember(members[0], members[1]);
        blockService.blockMember(members[0], members[2]);

        // when
        List<Member> blockList = memberService.findMemberBlockList(members[0].getMemberNo());

        // then
        assertEquals(2, blockList.size());
        assertEquals(members[1].getMemberNo(), blockList.get(0).getMemberNo());
        assertEquals(members[2].getMemberNo(), blockList.get(1).getMemberNo());
    }
    
    @Test
    void findMemberBlockList_실패() {
        NoMemberFoundException thrown = assertThrows(NoMemberFoundException.class, () -> memberService.findMemberBlockList(-1L));
        assertEquals(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, thrown.getMessage());
    }
}