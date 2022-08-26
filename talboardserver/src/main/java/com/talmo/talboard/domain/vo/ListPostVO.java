package com.talmo.talboard.domain.vo;

import com.talmo.talboard.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ListPostVO {
    @ApiModelProperty(value = "멤버 번호")
    Long memberNo;
    @ApiModelProperty(value = "글 번호")
    Long post_no;
    @ApiModelProperty(value = "글 제목")
    String title;
    @ApiModelProperty(value = "작성 날짜")
    String create_date;

    public ListPostVO(Post post) {
        this.memberNo = post.getMember().getMemberNo();
        this.post_no = post.getPost_no();
        this.title = post.getTitle();
        this.create_date = post.getCreate_date().toString();
    }
}
