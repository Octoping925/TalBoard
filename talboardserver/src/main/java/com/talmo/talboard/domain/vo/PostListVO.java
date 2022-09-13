package com.talmo.talboard.domain.vo;

import com.talmo.talboard.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostListVO {
    @ApiModelProperty(value = "멤버 번호")
    Long memberNo;
    @ApiModelProperty(value = "멤버 아이디")
    String memberId;
    @ApiModelProperty(value = "글 번호")
    Long postNo;
    @ApiModelProperty(value = "글 제목")
    String title;
    @ApiModelProperty(value = "작성 날짜")
    String createDate;

    public PostListVO(Post post) {
        this.memberNo = post.getMember().getMemberNo();
        this.memberId = post.getMember().getId();
        this.postNo = post.getPostNo();
        this.title = post.getTitle();
        this.createDate = post.getCreateDate().toString();
    }
}
