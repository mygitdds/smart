package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "批次访问model")
public class CreateBatchCodeReq {
    @ApiModelProperty(value = "卷码批次", required = true)
    private BatchCode batchCode;
    @ApiModelProperty(value = "链路id", required = true)
    private String requestId;
}
