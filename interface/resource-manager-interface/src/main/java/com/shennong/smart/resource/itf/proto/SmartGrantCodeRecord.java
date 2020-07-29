package com.shennong.smart.resource.itf.proto;

import lombok.Data;

import java.util.Date;

/**
 * SmartGrantCodeRecord
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/11
 */
@Data
public class SmartGrantCodeRecord {
    private Long id;

    private Long enterpriseId;

    private Long codeId;

    private String winningNumber;

    private String prizeId;

    private String openId;

    private String phonenumber;

    private String prizeType;

    private String activityId;

    private String businessParty;

    private String reason;

    private Date verifyTime;

    private String resourceName;

    private String code;

    private String examineName;

    private String examinePhone;

    private String cashprizePhone;

    private Date insertTime;

    private Date updateTime;
}
