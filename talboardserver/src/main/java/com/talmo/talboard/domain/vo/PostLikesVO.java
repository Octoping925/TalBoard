package com.talmo.talboard.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLikesVO {
    Long memberNo;
    boolean likeYn;

    public PostLikesVO(Long memberNo, boolean likeYn) {
        this.memberNo = memberNo;
        this.likeYn = likeYn;
    }
}
