package com.shennong.smart.resource.itf.proto.goods;

import lombok.Data;

/**
 *  DeleteGoodsReq
 * 类作用：
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
@Data
public class DeleteGoodsReq {
    private Long goodsId;
    private Long enterpriseId;
    private String requestId;
}
