package com.talmo.talboard.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.exception.NoMemberFoundException;
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
    public void save() {
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findOne_실패() {
        NoMemberFoundException thrown = assertThrows(NoMemberFoundException.class, () -> memberRepository.findOne(-1L));
    }

    @Test
    public void findById() {
    }

    @Test
    public void findActualMemberById() {
    }

    @Test
    public void findOneActualMemberById() {
    }

    @Test
    public void findActualMemberByEmailAddress() {
    }

    @Test
    public void findOneActualMemberByEmailAddress() {
    }

    @Test
    public void chkExistsActualMemberById() {
    }

    @Test
    public void chkExistsActualMemberByEmailAddress() {
    }
}