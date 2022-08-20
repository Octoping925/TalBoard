package com.talmo.talboard.service;

import com.talmo.talboard.domain.Block;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.repository.BlockRepository;
import com.talmo.talboard.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class BlockServiceTest {
    @Autowired BlockService blockService;
    @Autowired BlockRepository blockRepository;
    @Autowired MemberService memberService;

    String testId = "ididid";
    String testId2 = "ididid2";
    String testId3 = "ididid3";
    String testPw = "pwpwpw";
    String testEmail = "test@test.com";
    String testEmail2 = "test2@test.com";
    String testEmail3 = "test3@test.com";

    @Test
    public void cleanMember() {
        // given
        Member member = new Member(testId, testPw, testEmail);
        Member member2 = new Member(testId2, testPw, testEmail2);
        Member member3 = new Member(testId3, testPw, testEmail3);
        Long memberNo = memberService.join(member);
        Long memberNo2 = memberService.join(member2);
        Long memberNo3 = memberService.join(member3);

        blockService.blockMember(member, member2);
        blockService.blockMember(member3, member2);

        // when
        blockService.cleanMember(memberNo2);

        // then
        assertTrue(blockRepository.findBlock(memberNo, memberNo2).isEmpty());
        assertTrue(blockRepository.findBlock(memberNo3, memberNo2).isEmpty());
    }

    @Test
    public void blockMember() {
        // given
        Member member = new Member(testId, testPw, testEmail);
        Member member2 = new Member(testId2, testPw, testEmail2);
        Long memberNo = memberService.join(member);
        Long memberNo2 = memberService.join(member2);

        // when
        blockService.blockMember(member, member2);

        // then
        assertFalse(blockRepository.findBlock(memberNo, memberNo2).isEmpty());
    }

    @Test
    public void blockMember_실패() {
        // given
        Member member = new Member(testId, testPw, testEmail);
        Member member2 = new Member(testId2, testPw, testEmail2);
        memberService.join(member);
        memberService.join(member2);
        blockService.blockMember(member, member2);

        // when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> blockService.blockMember(member, member2));

        // then
        assertEquals("이미 차단 중인 회원", thrown.getMessage());
    }

    @Test
    public void unblockMember() {
        // given
        Member member = new Member(testId, testPw, testEmail);
        Member member2 = new Member(testId2, testPw, testEmail2);
        Long memberNo = memberService.join(member);
        Long memberNo2 = memberService.join(member2);

        // when
        blockService.blockMember(member, member2);
        blockService.unblockMember(member, member2);

        // then
        assertTrue(blockRepository.findBlock(memberNo, memberNo2).isEmpty());
    }

    @Test
    public void unblockMember_실패() {
        // given
        Member member = new Member(testId, testPw, testEmail);
        Member member2 = new Member(testId2, testPw, testEmail2);
        memberService.join(member);
        memberService.join(member2);

        // when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> blockService.unblockMember(member, member2));

        // then
        assertEquals("차단하지 않은 회원", thrown.getMessage());
    }
}