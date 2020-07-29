package com.shennong.smart.resource.itf.proto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@ApiModel(description = "卷码批次实体类")
public class BatchCode {
    /**
     * 自增id
     */
    @ApiModelProperty(value = "请求id", required = true)
    private Long id;

    /**
     * 主资源id
     */
    @ApiModelProperty(value = "主资源id", required = true)
    private Long resourceId;

    /**
     * 核销类型 consumer,terminal
     */
    @ApiModelProperty(value = "核销类型", required = true)
    private String verifyType;

    /**
     * 规则-采用json
     */
    @ApiModelProperty(value = "失效规则", required = true)
    private JSONObject  claimRules;

    /**
     * 卷码数量
     */
    @ApiModelProperty(value = "卷码新增数量", required = true)
    private Integer codeNumber;

    /**
     * 发放方式 api,export
     */
    @ApiModelProperty(value = "发放类型", required = true)
    private String grantType;

    /**
     * 防刷次数
     */
    @ApiModelProperty(value = "防止刷卷次数", required = true)
    private Integer brushCount;


    /**
     * 锁定时间
     */
    @ApiModelProperty(value = "锁定时间", required = true)
    private Integer lockTime;

    private Integer grantNum;
    private Integer invalidNum;
    private Integer verifyNum;

    private Long enterpriseId;

    private String claimRulesString;

}
