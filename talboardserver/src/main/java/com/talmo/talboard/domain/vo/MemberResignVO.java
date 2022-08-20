package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResignVO {
    @ApiModelProperty(value = "아이디", dataType = "string", required = true)
    String id;
    @ApiModelProperty(value = "탈퇴시킬 계정의 아이디", dataType = "string", required = true)
    String resign_member_id;
}
