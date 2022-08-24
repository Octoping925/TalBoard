package com.talmo.talboard.repository.service;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.vo.MemberDataChangeVO;
import com.talmo.talboard.exception.NoAuthorizationException;
import com.talmo.talboard.exception.NoMemberFoundException;
import com.talmo.talboard.repository.MemberRepository;
import java.util.List;

import com.talmo.talboard.service.BlockService;
import com.talmo.talboard.service.MemberService;
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
    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    BlockService blockService;

    @Test
    public void join() {
        // given
        Member member = TestHelper.createMember();

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
        Member member1 = TestHelper.createMember(TestHelper.testId, TestHelper.testPw, TestHelper.testEmail);
        Member member2 = TestHelper.createMember(TestHelper.testId, TestHelper.testPw2, TestHelper.testEmail2);

        // when
        memberService.join(member1);

        // then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 아이디 또는 이메일", thrown.getMessage());
    }

    @Test
    public void resign() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        member.setAdminYn(true);

        memberRepository.save(member);
        memberRepository.save(member2);

        // when
        memberService.resign(member.getMember_no(), member2.getMember_no());
        memberService.resign(member.getMember_no(), member.getMember_no());

        // then
        assertTrue(member.isResignYn());
        assertTrue(member2.isResignYn());
    }

    @Test
    public void resign_실패() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);

        memberRepository.save(member);
        memberRepository.save(member2);

        // when
        NoAuthorizationException thrown = assertThrows(NoAuthorizationException.class, () -> memberService.resign(member.getMember_no(), member2.getMember_no()));
        NoMemberFoundException thrown2 = assertThrows(NoMemberFoundException.class, () -> memberService.resign(member.getMember_no(),-1L));
        NoMemberFoundException thrown3 = assertThrows(NoMemberFoundException.class, () -> memberService.resign(-1L, member.getMember_no()));

        // then
        assertEquals("회원 탈퇴권한 없음", thrown.getMessage());
        assertEquals("회원 정보 찾지 못함", thrown2.getMessage());
        assertEquals("회원 정보 찾지 못함", thrown3.getMessage());
    }

    @Test
    public void findId() {
        // given
        Member member = TestHelper.createMember();
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
        Member member = TestHelper.createMember();
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
        Member member = TestHelper.createMember();
        memberRepository.save(member);

        MemberDataChangeVO vo = new MemberDataChangeVO();
        vo.setMember_no(member.getMember_no());
        vo.setPassword(TestHelper.testPw2);

        // when
        memberService.updateMemberData(member, vo);

        // then
        assertEquals(TestHelper.testPw2, memberRepository.findOne(member.getMember_no()).getPassword());
    }

    @Test
    public void updateMemberData_실패() {
        // given
        Member member = TestHelper.createMember();
        memberRepository.save(member);

        MemberDataChangeVO vo = new MemberDataChangeVO();
        vo.setMember_no(member.getMember_no());
        vo.setEmailAddress(member.getEmailAddress());

        // when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.updateMemberData(member, vo));

        // then
        assertEquals("이미 존재하는 이메일", thrown.getMessage());
    }

    @Test
    public void findMemberBlockList() {
        // given
        Member[] members = new Member[3];
        for(int i = 0; i < 3; ++i) {
            members[i] = TestHelper.createMember(i);
            memberService.join(members[i]);
        }
        blockService.blockMember(members[0], members[1]);
        blockService.blockMember(members[0], members[2]);

        // when
        List<Member> blockList = memberService.findMemberBlockList(members[0].getMember_no());

        // then
        assertEquals(2, blockList.size());
        assertEquals(members[1].getMember_no(), blockList.get(0).getMember_no());
        assertEquals(members[2].getMember_no(), blockList.get(1).getMember_no());
    }
    
    @Test
    public void findMemberBlockList_실패() {
        NoMemberFoundException thrown = assertThrows(NoMemberFoundException.class, () -> memberService.findMemberBlockList(-1L));
        assertEquals("회원 정보 찾지 못함", thrown.getMessage());
    }
}