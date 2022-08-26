package com.talmo.talboard.domain.vo;

import com.talmo.talboard.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostInfoVO {
    @ApiModelProperty(value = "멤버 번호", required = true)
    Long memberNo;
    @ApiModelProperty(value = "글 번호", required = true)
    Long post_no;
    @ApiModelProperty(value = "글 제목", required = true)
    String title;
    @ApiModelProperty(value = "글 내용", required = true)
    String context;

    public PostInfoVO(Long memberNo, Post post) {
        this.memberNo = memberNo;
        this.post_no = post.getPost_no();
        this.title = post.getTitle();
        this.context = post.getContext();
    }
}
