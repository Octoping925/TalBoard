package com.talmo.talboard.repository;

import com.talmo.talboard.domain.Member;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long memberNo) {
        return em.find(Member.class, memberNo);
    }

    public List<Member> findById(String id) {
        return em.createQuery("SELECT m FROM Member m WHERE m.id = :id", Member.class)
            .setParameter("id", id)
            .getResultList();
    }

    public Member findActualMemberById(String id) {
        return em.createQuery("SELECT m FROM Member m WHERE m.id = :id AND m.resignYn = false", Member.class)
            .setParameter("id", id)
            .getSingleResult();
    }

    public Member findActualMemberByEmailAddress(String emailAddress) {
        return em.createQuery("SELECT m FROM Member m WHERE m.emailAddress = :emailAddress AND m.resignYn = false", Member.class)
            .setParameter("emailAddress", emailAddress)
            .getSingleResult();
    }

}
