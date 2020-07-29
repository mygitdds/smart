package com.shennong.smart.resource.itf.proto.goods;

import lombok.Data;

/**
 * Classification
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
@Data
public class Classification {
    private Long id;

    private String classificationName;

    private Long enterpriseId;

    private Long parentId;

    private String insertTime;

    private String updateTime;
}
