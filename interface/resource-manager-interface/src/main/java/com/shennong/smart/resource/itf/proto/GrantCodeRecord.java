package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@ApiModel(description = "卷码发放实体类")
public class GrantCodeRecord {
    /**
     * 自增id
     */
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    /**
     * 卷码id
     */
    @ApiModelProperty(value = "codeId", required = true)
    private Long codeId;

    /**
     * 中奖标识
     */
    @ApiModelProperty(value = "中奖标识", required = true)
    private String winningNumber;

    /**
     * 奖品id
     */
    @ApiModelProperty(value = "奖品id", required = true)
    private Long prizeId;

    /**
     * 中奖C端用户的openid
     */
    @ApiModelProperty(value = "c端用户id", required = true)
    private String openId;

    /**
     * 中奖C端用户的手机号码
     */
    @ApiModelProperty(value = "中奖用户手机号", required = true)
    private String phonenumber;

    /**
     * 兑奖类型
     */
    @ApiModelProperty(value = "兑奖类型", required = true)
    private String prizeType;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id", required = true)
    private String activityId;

    /**
     * 业务方：例如uma
     */
    @ApiModelProperty(value = "业务方", required = true)
    private String businessParty;

    /**
     * 发放原因
     */
    @ApiModelProperty(value = "发放原因", required = true)
    private String reason;

    /**
     * 核销时间
     */
    @ApiModelProperty(value = "核销时间", required = true)
    private String verifyTime;

    /**
     * 核销资源名称
     */
    @ApiModelProperty(value = "资源名称", required = true)
    private String resourceName;

    /**
     * 被扫的卷码
     */
    @ApiModelProperty(value = "卷码code", required = true)
    private String code;

    /**
     * 核销员的名称
     */
    @ApiModelProperty(value = "核销人名称", required = true)
    private String examineName;

    /**
     * 核销员的手机号码
     */
    @ApiModelProperty(value = "核销人手机号码", required = false)
    private String examinePhone;

    /**
     * 领取人手机号码
     */
    @ApiModelProperty(value = "领取人手机号码", required = false)
    private String cashprizePhone;
}
