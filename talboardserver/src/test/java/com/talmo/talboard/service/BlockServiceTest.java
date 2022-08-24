package com.talmo.talboard.service;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Block;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.repository.BlockRepository;
import java.util.Objects;
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

    @Test
    public void cleanMember() {
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
//        assertTrue(blockRepository.findBlock(memberNo, memberNo2).isEmpty());
        assertTrue(member1.getBlockList().stream()
                .map(Block::getBlockedMember)
                .map(Member::getMember_no)
                .noneMatch(memberNo -> Objects.equals(memberNo, memberNo2)));

        assertTrue(blockRepository.findMemberBlockedList(memberNo2).isEmpty());

//        assertTrue(blockRepository.findBlock(memberNo2, memberNo3).isEmpty());
        assertTrue(member2.getBlockList().isEmpty());
    }

    @Test
    public void blockMember() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
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
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
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
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        Long memberNo = memberService.join(member);
        Long memberNo2 = memberService.join(member2);

        // when
        blockService.blockMember(member, member2);
        blockService.unblockMember(member, member2);

        // then
        assertTrue(blockRepository.findBlock(memberNo, memberNo2).isEmpty());
        assertTrue(member.getBlockList().isEmpty());
    }

    @Test
    public void unblockMember_실패() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        memberService.join(member);
        memberService.join(member2);

        // when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> blockService.unblockMember(member, member2));

        // then
        assertEquals("차단하지 않은 회원", thrown.getMessage());
    }
}