package com.talmo.talboard.repository;

import com.talmo.talboard.domain.Block;
import java.util.List;
import javax.persistence.EntityManager;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.id.BlockId;
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

    public Block find(Member member, Member blockMember) {
        return em.find(Block.class, new BlockId(member, blockMember));
    }

    @Deprecated
    public List<Block> findBlock(Long memberNo, Long blockMemberNo) {
        return em.createQuery("SELECT b FROM Block b " +
                        "WHERE b.blockId.member.memberNo = :memberNo" +
                        " AND b.blockId.blockedMember.memberNo = :blockMemberNo", Block.class)
                .setParameter("memberNo", memberNo)
                .setParameter("blockMemberNo", blockMemberNo)
                .getResultList();
    }

    @Deprecated
    public List<Block> findMemberBlockList(Long memberNo) {
        return em.createQuery("SELECT b FROM Block b WHERE b.blockId.member.memberNo = :memberNo", Block.class)
                .setParameter("memberNo", memberNo)
                .getResultList();
    }

    @Deprecated
    public List<Block> findMemberBlockedList(Long memberNo) {
        return em.createQuery("SELECT b FROM Block b WHERE b.blockId.blockedMember.memberNo = :memberNo", Block.class)
                .setParameter("memberNo", memberNo)
                .getResultList();
    }
}
