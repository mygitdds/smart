package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "查询主资源model")
public class SelectResourceReq {
    @ApiModelProperty(value = "链路Id", required = true)
    private String requestId;
    @ApiModelProperty(value = "资源id", required = false)
    private String resourceId;
    @ApiModelProperty(value = "资源名称", required = false)
    private String resourceName;
    @ApiModelProperty(value = "多少页", required = true)
    private int page;
    @ApiModelProperty(value = "多少行", required = true)
    private int rows;
    @ApiModelProperty(value = "企业id", required = true)
    private Long enterpriseId;

}
