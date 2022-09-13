package com.talmo.talboard.domain.vo;

import com.talmo.talboard.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequirementVO {
    @ApiModelProperty(value = "멤버 아이디")
    String memberId;

    @ApiModelProperty(value = "글 제목")
    String title;
}
