package com.talmo.talboard.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFindBlockListVO {
    @ApiModelProperty(value = "멤버 번호", required = true)
    Long memberNo;
}
