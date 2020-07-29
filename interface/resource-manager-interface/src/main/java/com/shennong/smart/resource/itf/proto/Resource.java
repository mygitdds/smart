package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/***
 *
 */
@Data
@ToString
@ApiModel(description = "主资源")
public class Resource {
    /**
     * 自增id
     */
    @ApiModelProperty(value = "链路id", required = false)
    private Long id;

    /**
     * 企业id
     */
    @ApiModelProperty(value = "企业id", required = true)
    private Long enterpriseId;

    /**
     * 外部资源图片
     */
    @ApiModelProperty(value = "图片地址", required = true)
    private String img;

    /**
     * 外部资源id
     */
    @ApiModelProperty(value = "外部资源id", required = true)
    private String resourceId;

    /**
     * 资源名称
     */
    @ApiModelProperty(value = "资源名称", required = true)
    private String name;

    /**
     * 资源类型
     */
    @ApiModelProperty(value = "资源类型", required = true)
    private String type;

    /**
     * 是否开启库存管理
     */
    @ApiModelProperty(value = "是否开启库存", required = true)
    private String stockManager;

    /**
     * 操作者
     */
    @ApiModelProperty(value = "操作者", required = false)
    private String operator;
    //插入时间
    private String insertTime;

    private Integer grantNum;
    private Integer invalidNum;
    private Integer verifyNum;
    private Integer total;
}
