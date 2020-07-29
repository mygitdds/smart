package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
@ApiModel(description = "查询主资源返回model")
public class SelectResourceRsp {
    @ApiModelProperty(value = "主资源列表", required = true)
    private List<Resource> codeList;
    @ApiModelProperty(value = "总数量", required = true)
    private int codeSum;
}
