package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {
    @ApiModelProperty(value = "아이디", dataType = "string", required = true)
    String id;

    @ApiModelProperty(value = "비밀번호", dataType = "string", required = true)
    String password;

    @ApiModelProperty(value = "이메일 주소", dataType = "string", required = true)
    String emailAddress;

//    @ApiModelProperty(value = "관리자 여부", dataType = "boolean")
//    boolean adminYn;
//
//    @ApiModelProperty(value = "탈퇴 여부", dataType = "boolean")
//    boolean resignYn;
}
