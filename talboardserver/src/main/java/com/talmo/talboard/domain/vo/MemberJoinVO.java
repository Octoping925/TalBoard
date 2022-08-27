package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinVO {
    @ApiModelProperty(value = "아이디", example = "abcd123", required = true)
    String id;

    @ApiModelProperty(value = "비밀번호", example = "zxcv123", required = true)
    String password;

    @ApiModelProperty(value = "이메일 주소", example = "talmo12@naver.com", required = true)
    String emailAddress;

    public MemberJoinVO(String id, String password, String emailAddress) {
        this.id = id;
        this.password = password;
        this.emailAddress = emailAddress;
    }
}
