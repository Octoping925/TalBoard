package com.talmo.talboard.domain.id;

import com.talmo.talboard.domain.Member;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;

@Getter
@Embeddable
public class BlockId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    Member member;
    @OneToOne(fetch = FetchType.LAZY)
    Member blockedMember;

    protected BlockId() {}

    public BlockId(Member member, Member blockedMember) {
        this.member = member;
        this.blockedMember = blockedMember;
    }

}
