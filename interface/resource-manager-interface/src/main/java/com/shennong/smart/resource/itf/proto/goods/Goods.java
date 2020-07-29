package com.shennong.smart.resource.itf.proto.goods;

import lombok.Data;

/**
 * Goods
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
@Data
public class Goods {
    private Long id;

    private Long enterpriseId;

    private String goodsName;

    private double price;

    private String goodsImg;

    private String briefIntroduction;

    private Long classificationId;

    private String classificationName;

    private String insertTime;

    private String updateTime;
}
