package com.shennong.smart.resource.itf.proto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "请求发卷的实体类")
public class GrantCodeRecordReq {
    @ApiModelProperty(value = "链路id", required = false)
    private String requestId;
    /**
     * 卷码id
     */
    @ApiModelProperty(value = "卷码id", required = true)
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
    private String prizeId;

    /**
     * 中奖C端用户的openid
     */
    @ApiModelProperty(value = "中奖用户id", required = true)
    private String openId;

    /**
     * 中奖C端用户的手机号码
     */
    @ApiModelProperty(value = "c端用户手机号码", required = true)
    private String phoneNumber;

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
    @ApiModelProperty(value = "核销员", required = true)
    private String reason;
}
