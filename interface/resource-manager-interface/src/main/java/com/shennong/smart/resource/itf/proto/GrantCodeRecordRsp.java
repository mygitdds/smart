package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "返回请求卷码model")
public class GrantCodeRecordRsp {
    @ApiModelProperty(value = "返回的数据code", required = true)
    private String code;
    @ApiModelProperty(value = "中奖标识", required = true)
    private String winningNumber;
}
