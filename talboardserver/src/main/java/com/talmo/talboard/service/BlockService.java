package com.talmo.talboard.service;

import com.talmo.talboard.domain.Block;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.exception.NoMemberFoundException;
import com.talmo.talboard.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;

    /**
     * 특정 회원의 차단 리스트와 특정 회원을 차단한 리스트를 전부 제거
     */
    public void cleanMember(Long member_no) {
        blockRepository.findMemberBlockList(member_no).forEach(blockRepository::delete);
        blockRepository.findMemberBlockedList(member_no).forEach(blockRepository::delete);
    }

    /**
     * 회원 차단
     */
    public void blockMember(Member member, Member blockMember) {
        List<Block> blockList = blockRepository.findBlock(member.getMember_no(), blockMember.getMember_no());

        if(!blockList.isEmpty()) {
            throw new IllegalStateException("이미 차단 중인 회원");
        }

        Block block = new Block(member, blockMember);
        blockRepository.save(block);
    }

    /**
     * 회원 차단 해제
     */
    public void unblockMember(Member member, Member blockMember) {
        List<Block> blockList = blockRepository.findBlock(member.getMember_no(), blockMember.getMember_no());

        if(blockList.isEmpty()) {
            throw new IllegalStateException("차단하지 않은 회원");
        }

        blockRepository.delete(blockList.get(0));
    }

}
