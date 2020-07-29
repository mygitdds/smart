package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "查询批次model")
public class SelectCodeReq {
    @ApiModelProperty(value = "主资源id", required = true)
    private String resourceId;
    @ApiModelProperty(value = "链路id", required = true)
    private String requestId;
    private int page;
    private int rows;
}
