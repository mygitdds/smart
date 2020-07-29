package com.shennong.smart.resource.itf.proto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CreateCodeBatch {
    private List<String> codes;
    private Long enterpriseId;
    private Long batchCodeId;
    private Long resourceId;
    private String codeStatus;
    private int edition;

}
