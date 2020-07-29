package com.shennong.smart.resource.itf.proto.goods;

import lombok.Data;

/**
 * Product
 * 类作用：
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
@Data
public class Product {
    private Long id;

    private Long enterpriseId;

    private String productName;

    private String goodsList;

    private double price;

    private String goodsImg;

    private String insertTime;

    private String updateTime;
}
