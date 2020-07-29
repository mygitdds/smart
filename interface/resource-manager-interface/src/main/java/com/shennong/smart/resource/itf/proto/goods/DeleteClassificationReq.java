package com.shennong.smart.resource.itf.proto.goods;

import lombok.Data;

/**
 * DeleteClassificationReq
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/11
 */
@Data
public class DeleteClassificationReq {
    private Long enterpriseId;
    private Long classificationId;
}
