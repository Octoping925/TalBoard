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

    public Block findOne(Member member, Member blockMember) {
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

    /**
     * @deprecated 해당 멤버가 차단한 유저들이 DB에 제대로 들어있는지 확인 용으로만 사용, Member::getBlockList 사용 권장
     */
    @Deprecated
    public List<Block> findMemberBlockList(Long memberNo) {
        return em.createQuery("SELECT b FROM Block b WHERE b.blockId.member.memberNo = :memberNo", Block.class)
                .setParameter("memberNo", memberNo)
                .getResultList();
    }

    /**
     * @deprecated 해당 멤버를 차단한 유저들이 DB에 제대로 들어있는지 확인 용으로만 사용, Member::getBlockedList 사용 권장
     */
    @Deprecated
    public List<Block> findMemberBlockedList(Long memberNo) {
        return em.createQuery("SELECT b FROM Block b WHERE b.blockId.blockedMember.memberNo = :memberNo", Block.class)
                .setParameter("memberNo", memberNo)
                .getResultList();
    }
}
