package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberBlockVO {
    @ApiModelProperty(value = "아이디", required = true)
    String id;
    @ApiModelProperty(value = "차단 유저 아이디", required = true)
    String blocked_member_id;
}