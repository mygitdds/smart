package com.shennong.smart.resource.itf.proto.goods;

import lombok.Data;

import java.util.List;

/**
 * SelectGoodsRsp
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
@Data
public class SelectGoodsRsp {
    private List<Goods> goodsList;
}
