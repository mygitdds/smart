package com.shennong.smart.resource.manager.config.sharding;

/**
 * ResourceDbAndTableEnum
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/9
 */

public enum ResourceDbAndTableEnum {

    T_SMART_CODE("t_smart_code", "enterpriseId", "01", "01", "HT", 2,2, 4, 2, 4, "码表"),
    T_SMART_RESOURCE("t_smart_resource", "enterpriseId", "01", "01", "HP", 2,2, 4, 2, 2, "主资源表"),
    T_SMART_BATCH_CODE("t_smart_batch_code", "enterpriseId", "01", "01", "XQ", 2,2, 4, 2, 2, "批次表"),
    T_SMART_GRANT_CODE_RECORD("t_smart_grant_code_record", "enterpriseId", "01", "01", "XH", 2,2, 4, 2, 2, "核销表"),
    T_SMART_GOODS("t_smart_goods", "enterpriseId", "01", "01", "HT", 2,2, 4, 2, 2, "商品表"),
    T_SMART_CLASSIFICATION("t_smart_classification", "enterpriseId", "01", "01", "HP", 2,2, 4, 2, 2, "分类表"),
    T_SMART_PRODUCT("t_smart_product", "enterpriseId", "01", "01", "XQ", 2,2, 4, 2, 2, "产品表");

    /**分片表名*/
    private String tableName;
    /**分片键*/
    private String shardingKey;
    /**系统标识*/
    private String bizType;
    /**主键规则版本*/
    private String idVersion;
    /**表名字母前缀*/
    private String charsPrefix;
    /**分片键值中纯数字起始下标索引，第一位是0,第二位是1，依次类推*/
    private int numberStartIndex;
    /**数据库索引位开始下标索引*/
    private int dbIndexBegin;
    /**表索引位开始下标索引*/
    private int tbIndexBegin;
    /**分布所在库数量*/
    private int dbCount;
    /**分布所在表数量-所有库中表数量总计*/
    private int tbCount;
    /**描述*/
    private String desc;

    ResourceDbAndTableEnum(String tableName, String shardingKey, String bizType, String idVersion, String charsPrefix,
                           int numberStartIndex, int dbIndexBegin, int tbIndexBegin, int dbCount, int tbCount, String desc) {
        this.tableName = tableName;
        this.shardingKey = shardingKey;
        this.bizType = bizType;
        this.idVersion = idVersion;
        this.charsPrefix = charsPrefix;
        this.numberStartIndex = numberStartIndex;
        this.dbIndexBegin = dbIndexBegin;
        this.tbIndexBegin = tbIndexBegin;
        this.dbCount = dbCount;
        this.tbCount = tbCount;
        this.desc = desc;
    }
    public String getTableName() {
        return tableName;
    }

    public String getShardingKey() {
        return shardingKey;
    }

    public String getBizType() {
        return bizType;
    }

    public String getIdVersion() {
        return idVersion;
    }

    public String getCharsPrefix() {
        return charsPrefix;
    }

    public int getNumberStartIndex() {
        return numberStartIndex;
    }

    public int getDbIndexBegin() {
        return dbIndexBegin;
    }

    public int getTbIndexBegin() {
        return tbIndexBegin;
    }

    public int getDbCount() {
        return dbCount;
    }

    public int getTbCount() {
        return tbCount;
    }

    public String getDesc() {
        return desc;
    }
}
