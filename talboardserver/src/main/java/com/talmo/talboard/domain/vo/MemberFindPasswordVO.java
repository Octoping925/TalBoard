package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFindPasswordVO {
    @ApiModelProperty(value = "아이디", required = true)
    String id;
}
