package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberNoVO {
    @ApiModelProperty(value = "멤버 번호", required = true)
    Long member_no;
}
