package com.talmo.talboard.domain;

import com.talmo.talboard.domain.id.BlockId;
import lombok.Getter;

import java.time.LocalDateTime;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Entity
public class Block {
    @EmbeddedId
    private BlockId blockId;
    private LocalDateTime createDate;

    //==연관관계 메서드==//
    public void unblock() {
        this.getMember().getBlockList().remove(this);
        this.getBlockedMember().getBlockedList().remove(this);
    }

    //==생성 메서드==//
    protected Block() {
        this.createDate = LocalDateTime.now();
    }

    private Block(Member member, Member blockMember) {
        this.blockId = new BlockId(member, blockMember);
        this.createDate = LocalDateTime.now();
    }

    public static Block createBlock(Member member, Member blockMember) {
        Block block = new Block(member, blockMember);
        member.getBlockList().add(block);
        blockMember.getBlockedList().add(block);
        return block;
    }

    //==조회 메서드==//
    public Member getMember() {
        return this.blockId.getMember();
    }

    public Member getBlockedMember() {
        return this.blockId.getBlockedMember();
    }
}
