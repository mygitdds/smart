package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@ApiModel(description = "查询批次返回model")
public class SelectCodeRsp {
   @ApiModelProperty(value = "批次数组", required = true)
   private List<BatchCode> codeList;
   @ApiModelProperty(value = "总数", required = true)
   private int codeSum;
}
