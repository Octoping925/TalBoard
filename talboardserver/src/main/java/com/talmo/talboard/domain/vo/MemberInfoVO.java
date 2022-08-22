package com.talmo.talboard.domain.vo;

import com.talmo.talboard.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberInfoVO {
    @ApiModelProperty(value = "아이디")
    String id;
    @ApiModelProperty(value = "이메일 주소")
    String emailAddress;

    public MemberInfoVO(Member member) {
        this.id = member.getId();
        this.emailAddress = member.getEmailAddress();
    }
}
