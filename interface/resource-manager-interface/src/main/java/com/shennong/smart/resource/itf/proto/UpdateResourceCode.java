package com.shennong.smart.resource.itf.proto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpdateResourceCode {
    private Long resourceId;
    private Integer grantNum;
    private Integer invalidNum;
    private Integer verifyNum;
    private Integer total;
}
