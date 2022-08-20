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

    protected Block() {
        this.createDate = LocalDateTime.now();
    }

    public Block(Member member, Member blockMember) {
        this.blockId = new BlockId(member, blockMember);
        this.createDate = LocalDateTime.now();
    }

    public Member getMember() {
        return this.blockId.getMember();
    }

    public Member getBlockedMember() {
        return this.blockId.getBlockedMember();
    }
}
