package com.talmo.talboard.service;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.repository.BlockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class BlockServiceTest {
    @Autowired BlockService blockService;
    @Autowired BlockRepository blockRepository;
    @Autowired MemberService memberService;

    @Test
    void cleanMember() {
        // given
        Member member1 = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        Member member3 = TestHelper.createMember(3);
        memberService.join(member1);
        Long memberNo2 = memberService.join(member2);
        memberService.join(member3);

        blockService.blockMember(member1, member2);
        blockService.blockMember(member1, member3);
        blockService.blockMember(member2, member3);

        // when
        blockService.cleanMember(member2);

        // then
        assertNull(blockRepository.findOne(member1, member2));
        assertNull(blockRepository.findOne(member2, member3));
        assertFalse(member1.getBlockMembers().contains(member2));

        assertTrue(member2.getBlockList().isEmpty());
        assertTrue(member2.getBlockedList().isEmpty());
        assertFalse(member3.getBlockedMembers().contains(member2));
    }

    @Test
    void blockMember() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        memberService.join(member);
        memberService.join(member2);

        // when
        blockService.blockMember(member, member2);

        // then
        assertNotNull(blockRepository.findOne(member, member2));
        assertEquals(1, member.getBlockList().size());
        assertTrue(member.isBlockMember(member2));
        assertTrue(member.getBlockMembers().contains(member2));
        assertTrue(member2.getBlockedMembers().contains(member));
    }

    @Test
    void blockMember_중복차단() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        memberService.join(member);
        memberService.join(member2);
        blockService.blockMember(member, member2);

        // when
        blockService.blockMember(member, member2);

        // then
        assertNotNull(blockRepository.findOne(member, member2));
        assertEquals(1, member.getBlockList().size());
        assertEquals(1, member2.getBlockedList().size());
        assertTrue(member.getBlockMembers().contains(member2));
        assertTrue(member2.getBlockedMembers().contains(member));
    }

    @Test
    void unblockMember() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        memberService.join(member);
        memberService.join(member2);
        blockService.blockMember(member, member2);

        // when
        blockService.unblockMember(member, member2);

        // then
        assertNull(blockRepository.findOne(member, member2));
        assertTrue(member.getBlockList().isEmpty());
        assertTrue(member2.getBlockedList().isEmpty());
        assertFalse(member.isBlockMember(member2));
    }

    @Test
    void unblockMember_중복해제() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        memberService.join(member);
        memberService.join(member2);

        // when
        blockService.unblockMember(member, member2);

        // then
        assertNull(blockRepository.findOne(member, member2));
        assertTrue(member.getBlockList().isEmpty());
        assertTrue(member2.getBlockedList().isEmpty());
    }
}