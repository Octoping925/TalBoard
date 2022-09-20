package com.talmo.talboard.domain.vo;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeInfoVO {
    @ApiModelProperty(value = "글 번호")
    Long postNo;

    @ApiModelProperty(value = "글 제목")
    String postTitle;

    @ApiModelProperty(value = "댓글 작성자 아이디")
    String commentWriterId;

    public NoticeInfoVO(Post post, Member commentWriter) {
        this.postNo = post.getPostNo();
        this.postTitle = post.getTitle();
        this.commentWriterId = commentWriter.getId();
    }
}
