package com.talmo.talboard.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.exception.NoMemberFoundException;
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
public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    public void save_findOne() {
        // given
        Member member = TestHelper.createMember();

        // when
        memberRepository.save(member);

        // then
        assertEquals(memberRepository.findOne(member.getMember_no()), member);
    }

    @Test
    public void findOne_실패() {
        assertThrows(NoMemberFoundException.class, () -> memberRepository.findOne(-1L));
    }

    @Test
    public void findActualMemberById() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        memberRepository.save(member);
        memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findActualMemberById(member.getId());

        // then
        assertEquals(1, members.size());
        assertEquals(member, members.get(0));
    }

    @Test
    public void findActualMemberById_EmptyResult() {
        List<Member> members = memberRepository.findActualMemberById("cantExist");
        assertEquals(0, members.size());
    }

    @Test
    public void findOneActualMemberById() {
        // given
        Member member = TestHelper.createMember();
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findOneActualMemberById(member.getId());

        // then
        assertEquals(member, findMember);
    }

    @Test
    public void findOneActualMemberById_실패() {
        assertThrows(NoMemberFoundException.class, () -> memberRepository.findOneActualMemberById("cantExist"));
    }

    @Test
    public void findActualMemberByEmailAddress() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        memberRepository.save(member);
        memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findActualMemberByEmailAddress(member.getEmailAddress());

        // then
        assertEquals(1, members.size());
        assertEquals(member, members.get(0));
    }

    @Test
    public void findOneActualMemberByEmailAddress_실패() {
        assertThrows(NoMemberFoundException.class, () -> memberRepository.findOneActualMemberByEmailAddress("cantExist"));
    }

    @Test
    public void findOneActualMemberByEmailAddress() {
        // given
        Member member = TestHelper.createMember();
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findOneActualMemberByEmailAddress(member.getEmailAddress());

        // then
        assertEquals(member, findMember);
    }

    @Test
    public void chkExistsActualMemberById() {
        // given
        Member member = TestHelper.createMember();

        // when
        boolean resultFalse = memberRepository.chkExistsActualMemberById(member.getId());
        memberRepository.save(member);
        boolean resultTrue = memberRepository.chkExistsActualMemberById(member.getId());

        // then
        assertFalse(resultFalse);
        assertTrue(resultTrue);
    }

    @Test
    public void chkExistsActualMemberByEmailAddress() {
        // given
        Member member = TestHelper.createMember();

        // when
        boolean resultFalse = memberRepository.chkExistsActualMemberByEmailAddress(member.getEmailAddress());
        memberRepository.save(member);
        boolean resultTrue = memberRepository.chkExistsActualMemberByEmailAddress(member.getEmailAddress());

        // then
        assertFalse(resultFalse);
        assertTrue(resultTrue);
    }
}