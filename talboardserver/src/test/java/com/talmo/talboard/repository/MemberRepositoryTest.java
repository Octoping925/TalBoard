package com.talmo.talboard.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.exception.NoMemberFoundException;
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
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    Member member1, member2;

    @BeforeEach
    void beforeEach() {
        member1 = TestHelper.createMember(1);
        member2 = TestHelper.createMember(2);
    }

    @Test
    void save_findOne() {
        // given
        // when
        memberRepository.save(member1);

        // then
        assertEquals(memberRepository.findOne(member1.getMemberNo()), member1);
    }

    @Test
    void findOne_실패() {
        assertThrows(NoMemberFoundException.class, () -> memberRepository.findOne(-1L));
    }

    @Test
    void findActualMemberById() {
        // given
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findActualMemberById(member1.getId());

        // then
        assertEquals(1, members.size());
        assertEquals(member1, members.get(0));
    }

    @Test
    void findActualMemberById_EmptyResult() {
        List<Member> members = memberRepository.findActualMemberById("cantExist");
        assertEquals(0, members.size());
    }

    @Test
    void findOneActualMemberById() {
        // given
        memberRepository.save(member1);

        // when
        Member findMember = memberRepository.findOneActualMemberById(member1.getId());

        // then
        assertEquals(member1, findMember);
    }

    @Test
    void findOneActualMemberById_실패() {
        assertThrows(NoMemberFoundException.class, () -> memberRepository.findOneActualMemberById("cantExist"));
    }

    @Test
    void findActualMemberByEmailAddress() {
        // given
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findActualMemberByEmailAddress(member1.getEmailAddress());

        // then
        assertEquals(1, members.size());
        assertEquals(member1, members.get(0));
    }

    @Test
    void findOneActualMemberByEmailAddress_실패() {
        assertThrows(NoMemberFoundException.class, () -> memberRepository.findOneActualMemberByEmailAddress("cantExist"));
    }

    @Test
    void findOneActualMemberByEmailAddress() {
        // given
        memberRepository.save(member1);

        // when
        Member findMember = memberRepository.findOneActualMemberByEmailAddress(member1.getEmailAddress());

        // then
        assertEquals(member1, findMember);
    }

    @Test
    void chkExistsActualMemberById() {
        // given
        // when
        boolean resultFalse = memberRepository.chkExistsActualMemberById(member1.getId());
        memberRepository.save(member1);
        boolean resultTrue = memberRepository.chkExistsActualMemberById(member1.getId());

        // then
        assertFalse(resultFalse);
        assertTrue(resultTrue);
    }

    @Test
    void chkExistsActualMemberByEmailAddress() {
        // given
        // when
        boolean resultFalse = memberRepository.chkExistsActualMemberByEmailAddress(member1.getEmailAddress());
        memberRepository.save(member1);
        boolean resultTrue = memberRepository.chkExistsActualMemberByEmailAddress(member1.getEmailAddress());

        // then
        assertFalse(resultFalse);
        assertTrue(resultTrue);
    }
}