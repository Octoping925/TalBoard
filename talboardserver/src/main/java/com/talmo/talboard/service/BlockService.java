package com.talmo.talboard.service;

import com.talmo.talboard.domain.Block;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;

    /**
     * 특정 회원의 차단 리스트와 특정 회원을 차단한 리스트를 전부 제거
     */
    @Transactional
    public void cleanMember(Member member) {
        // 특정 회원이 차단한 회원을 전부 차단 해제
        member.getBlockList().forEach(block -> unblockMember(member, block.getBlockedMember()));

        // 특정 회원을 차단한 회원의 차단을 전부 해제
        member.getBlockedList().forEach(block -> unblockMember(block.getMember(), member));
    }

    /**
     * 회원 차단
     */
    @Transactional
    public void blockMember(Member member, Member blockMember) {
        boolean isAlreadyBlocked = member.isBlockMember(blockMember);
        if(!isAlreadyBlocked) {
            Block block = Block.createBlock(member, blockMember);
            blockRepository.save(block);
        }
    }

    /**
     * 회원 차단 해제
     */
    @Transactional
    public void unblockMember(Member member, Member blockMember) {
        Block block = blockRepository.findOne(member, blockMember);

        if(block != null) {
            block.unblock();
            blockRepository.delete(block);
        }
    }

    @Transactional
    public void unblockMember(Block block) {
        blockRepository.delete(block);
    }

}
