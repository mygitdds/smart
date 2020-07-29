package com.shennong.smart.resource.itf.proto.goods;

import lombok.Data;

/**
 * SelectProductReq
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
@Data
public class SelectProductReq {
    private Long classificationId;
    private String requestId;
    private Long enterpriseId;
}
