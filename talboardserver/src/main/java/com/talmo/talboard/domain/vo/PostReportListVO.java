package com.talmo.talboard.domain.vo;

import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.Report;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReportListVO {
    @ApiModelProperty(value = "신고 멤버 번호", required = true)
    Long memberNo;
    @ApiModelProperty(value = "신고 게시글 번호", required = true)
    Long postNo;

    public PostReportListVO(Report report) {
        this.memberNo = report.getReportMember().getMemberNo();
        this.postNo = report.getReportedPost().getPostNo();
    }
}
