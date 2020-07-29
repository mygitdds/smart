package com.shennong.smart.resource.itf.proto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpdateBatchCode {
    private Long batchCodeId;
    private Integer grantNum;
    private Integer invalidNum;
    private Integer verifyNum;
}
