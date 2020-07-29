package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "创建主资源model")
public class CreateResourceReq {
    @ApiModelProperty(value = "主资源", required = true)
    private Resource resource;
    @ApiModelProperty(value = "链路id", required = true)
    private String requestId;
}
