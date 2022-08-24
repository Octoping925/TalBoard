package com.talmo.talboard.service;

import com.talmo.talboard.domain.Block;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.repository.BlockRepository;
import java.util.List;
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
        member.getBlockList().forEach(blockRepository::delete);
        member.cleanBlockList();

        blockRepository.findMemberBlockedList(member.getMember_no()).forEach(block -> unblockMember(block.getMember(), member));
    }

    /**
     * 회원 차단
     */
    @Transactional
    public void blockMember(Member member, Member blockMember) {
        boolean isAlreadyBlocked = member.isBlockMember(blockMember);
        if(isAlreadyBlocked) {
            throw new IllegalStateException("이미 차단 중인 회원");
        }

        Block block = Block.createBlock(member, blockMember);
        blockRepository.save(block);
    }

    /**
     * 회원 차단 해제
     */
    @Transactional
    public void unblockMember(Member member, Member blockMember) {
        boolean isAlreadyBlocked = member.isBlockMember(blockMember);

        if(!isAlreadyBlocked) {
            throw new IllegalStateException("차단하지 않은 회원");
        }

        List<Block> blockList = member.getBlockList();
        for(int i = 0; i < blockList.size(); ++i) {
            Block block = blockList.get(i);
            if (blockMember.equals(block.getBlockedMember())) {
                blockRepository.delete(block);
                blockList.remove(i);
                return;
            }
        }
    }

}
