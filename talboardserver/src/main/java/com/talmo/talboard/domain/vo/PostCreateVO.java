package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateVO {
    @ApiModelProperty(value="작성자", required = true)
    Long memberNo;

    @ApiModelProperty(value="게시글 제목", required = true)
    String title;

    @ApiModelProperty(value="게시글 내용", required = true)
    String context;

    public PostCreateVO(String title, String context) {
        this.title = title;
        this.context = context;
    }
}
