package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinVO {
    @ApiModelProperty(value = "아이디", dataType = "string", required = true)
    String id;

    @ApiModelProperty(value = "비밀번호", dataType = "string", required = true)
    String password;

    @ApiModelProperty(value = "이메일 주소", dataType = "string", required = true)
    String emailAddress;
}
