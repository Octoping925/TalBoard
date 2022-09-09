package com.talmo.talboard.domain.vo;

import com.talmo.talboard.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDetailVO {
    @ApiModelProperty(value = "멤버 번호", required = true)
    Long member_no;
    @ApiModelProperty(value = "게시글 번호", required = true)
    Long post_no;
    @ApiModelProperty(value = "게시글 제목", required = true)
    String title;
    @ApiModelProperty(value = "게시글 내용", required = true)
    String context;
    @ApiModelProperty(value = "작성일자", required = true)
    String create_date;


    public PostDetailVO(Post post) {
        this.member_no = post.getMember().getMemberNo();
        this.post_no = post.getPost_no();
        this.title = post.getTitle();
        this.context = post.getContext();
        this.create_date = post.getCreate_date().toString();
    }
}
