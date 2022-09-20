package com.talmo.talboard.domain.vo;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Notice;
import com.talmo.talboard.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeInfoVO {
    @ApiModelProperty(value = "알림 번호")
    Long noticeNo;

    @ApiModelProperty(value = "글 번호")
    Long postNo;

    @ApiModelProperty(value = "글 제목")
    String postTitle;

    @ApiModelProperty(value = "댓글 작성자 아이디")
    String commentWriterId;

//    public NoticeInfoVO(Notice notice) {
//        Post post = notice.getComment().getPost();
//        this.postNo = notice.getComment().getPost();
//        this.postTitle = notice.getComment().getTitle();
//        this.commentWriterId = notice.getComment().getMember().getId();
//    }
}
