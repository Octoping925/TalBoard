package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFindIdVO {
    @ApiModelProperty(value = "이메일 주소", required = true)
    String emailAddress;
}
