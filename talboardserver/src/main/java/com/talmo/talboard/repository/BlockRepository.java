package com.talmo.talboard.repository;

import com.talmo.talboard.domain.Block;
import com.talmo.talboard.domain.Member;
import java.util.List;
import javax.persistence.EntityManager;

import com.talmo.talboard.exception.NoMemberFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlockRepository {
    private final EntityManager em;

    public void save(Block block) {
        em.persist(block);
    }

    public void delete(Block block) {
        em.remove(block);
    }

    public List<Block> findBlock(Long member_no, Long blockMemberNo) {
        return em.createQuery("SELECT b FROM Block b " +
                        "WHERE b.blockId.member.member_no = :member_no" +
                        " AND b.blockId.blockedMember.member_no = :block_member_no", Block.class)
                .setParameter("member_no", member_no)
                .setParameter("block_member_no", blockMemberNo)
                .getResultList();
    }

    public List<Block> findMemberBlockList(Long member_no) {
        return em.createQuery("SELECT b FROM Block b WHERE b.blockId.member.member_no = :member_no", Block.class)
                .setParameter("member_no", member_no)
                .getResultList();
    }

    public List<Block> findMemberBlockedList(Long member_no) {
        return em.createQuery("SELECT b FROM Block b WHERE b.blockId.blockedMember.member_no = :member_no", Block.class)
                .setParameter("member_no", member_no)
                .getResultList();
    }
}
