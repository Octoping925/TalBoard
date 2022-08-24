package com.talmo.talboard.repository;

import com.talmo.talboard.domain.Member;
import java.util.List;
import javax.persistence.EntityManager;

import com.talmo.talboard.exception.NoMemberFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member) {
        if(member.getMember_no() == null) {
            em.persist(member);
        }
        else {
            em.merge(member);
        }
    }

    public Member findOne(Long memberNo) {
        Member member = em.find(Member.class, memberNo);
        if(member == null) throw new NoMemberFoundException();
        return member;
    }

    public List<Member> findById(String id) {
        return em.createQuery("SELECT m FROM Member m WHERE m.id = :id", Member.class)
            .setParameter("id", id)
            .getResultList();
    }

    public List<Member> findActualMemberById(String id) {
        return em.createQuery("SELECT m FROM Member m WHERE m.id = :id AND m.resignYn = false", Member.class)
            .setParameter("id", id)
            .getResultList();
    }

    public Member findOneActualMemberById(String id) {
        List<Member> actualMember = findActualMemberById(id);
        if(actualMember.isEmpty()) {
            throw new NoMemberFoundException();
        }
        return actualMember.get(0);
    }

    public List<Member> findActualMemberByEmailAddress(String emailAddress) {
        return em.createQuery("SELECT m FROM Member m WHERE m.emailAddress = :emailAddress AND m.resignYn = false", Member.class)
            .setParameter("emailAddress", emailAddress)
            .getResultList();
    }

    public Member findOneActualMemberByEmailAddress(String emailAddress) {
        List<Member> actualMember = findActualMemberByEmailAddress(emailAddress);
        if(actualMember.isEmpty()) {
            throw new NoMemberFoundException();
        }
        return actualMember.get(0);
    }

    public boolean chkExistsActualMemberById(String id) {
        return em.createQuery("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END "
                + "FROM Member m WHERE m.id = :id AND m.resignYn = false", Boolean.class)
            .setParameter("id", id)
            .getSingleResult();
    }

    public boolean chkExistsActualMemberByEmailAddress(String emailAddress) {
        return em.createQuery("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END "
                + "FROM Member m WHERE m.emailAddress = :emailAddress AND m.resignYn = false", Boolean.class)
            .setParameter("emailAddress", emailAddress)
            .getSingleResult();
    }

}
