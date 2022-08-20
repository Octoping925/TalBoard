package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFindPasswordVO {
    @ApiModelProperty(value = "아이디", dataType = "string", required = true)
    String id;
}
