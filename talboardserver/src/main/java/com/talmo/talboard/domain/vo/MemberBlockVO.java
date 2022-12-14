package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberBlockVO {
    @ApiModelProperty(value = "멤버 번호", required = true)
    Long memberNo;
    @ApiModelProperty(value = "차단 유저 멤버 번호", required = true)
    Long blockedMemberNo;
}