package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@ApiModel(description = "卷实体类")
public class Code {
    /**
     * 自增id
     */
    @ApiModelProperty(value = "请求id", required = true)
    private Long id;

    /**
     * 批次表id
     */
    @ApiModelProperty(value = "批次id", required = true)
    private Long batchCodeId;

    /**
     * 企业id
     */
    @ApiModelProperty(value = "企业id", required = true)
    private Long enterpriseId;

    /**
     * 被扫的卷码
     */
    @ApiModelProperty(value = "被扫的卷码", required = false)
    private String code;

    /**
     * 卷码状态 1,已发放，2已核销，3无效
     */
    @ApiModelProperty(value = "卷码状态", required = true)
    private String codeStatus;

    /**
     * 核销时间
     */
    @ApiModelProperty(value = "核销时间", required = false)
    private String verifyTime;

    /**
     * 插入时间
     */
    @ApiModelProperty(value = "插入时间", required = false)
    private Date insertTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 版本号
     */
    private int edition;
    //操作者
    private String executor;

}
