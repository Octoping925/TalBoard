package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReportVO {
    @ApiModelProperty(value = "신고 멤버 번호", required = true)
    Long memberNo;
    @ApiModelProperty(value = "신고 게시글 번호", required = true)
    Long postNo;
}
