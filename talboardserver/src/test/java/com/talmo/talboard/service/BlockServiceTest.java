package com.talmo.talboard.service;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.repository.BlockRepository;
import com.talmo.talboard.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired MemberRepository memberRepository;

    Member member1, member2, member3;

    @BeforeEach
    void beforeEach() {
        member1 = TestHelper.createMember(1);
        member2 = TestHelper.createMember(2);
        member3 = TestHelper.createMember(3);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

    @Test
    void cleanMember() {
        // given
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
        // when
        blockService.blockMember(member1, member2);

        // then
        assertNotNull(blockRepository.findOne(member1, member2));
        assertEquals(1, member1.getBlockList().size());
        assertTrue(member1.isBlockMember(member2));
        assertTrue(member1.getBlockMembers().contains(member2));
        assertTrue(member2.getBlockedMembers().contains(member1));
    }

    @Test
    void blockMember_중복차단() {
        // given
        blockService.blockMember(member1, member2);

        // when
        blockService.blockMember(member1, member2);

        // then
        assertNotNull(blockRepository.findOne(member1, member2));
        assertEquals(1, member1.getBlockList().size());
        assertEquals(1, member2.getBlockedList().size());
        assertTrue(member1.getBlockMembers().contains(member2));
        assertTrue(member2.getBlockedMembers().contains(member1));
    }

    @Test
    void unblockMember() {
        // given
        blockService.blockMember(member1, member2);

        // when
        blockService.unblockMember(member1, member2);

        // then
        assertNull(blockRepository.findOne(member1, member2));
        assertTrue(member1.getBlockList().isEmpty());
        assertTrue(member2.getBlockedList().isEmpty());
        assertFalse(member1.isBlockMember(member2));
    }

    @Test
    void unblockMember_중복해제() {
        // given
        // when
        blockService.unblockMember(member1, member2);

        // then
        assertNull(blockRepository.findOne(member1, member2));
        assertTrue(member1.getBlockList().isEmpty());
        assertTrue(member2.getBlockedList().isEmpty());
    }
}