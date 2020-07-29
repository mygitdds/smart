package com.shennong.smart.resource.manager.config.sharding;

import com.shennong.smart.common.base.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * ResourceSubTreasury
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/9
 */
public class ResourceSubTreasury implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {

        return selectLibraryName(collection,preciseShardingValue);
    }

    public String selectLibraryName(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        String dbs = "";
        //通过循环匹配到相应的库--返回相应的库
        for (ResourceDbAndTableEnum targetEnum : ResourceDbAndTableEnum.values()) {
            if (targetEnum.getTableName().equals(shardingValue.getLogicTableName())) {
                //目标表的目标主键路由-例如：根据订单id查询订单信息
                if (targetEnum.getShardingKey().equals(shardingValue.getColumnName())) {
                    int dbNnmber = shardingValue.getValue().intValue() % targetEnum.getDbCount();

                    for (String availableTargetName : availableTargetNames){
                        String nameSuffix = availableTargetName.substring(ResourceShardingConstant.LOGIC_DB_PREFIX_LENGTH);
                        if (nameSuffix.equals(dbNnmber)) {
                            dbs = availableTargetName;
                            break;
                        }
                    }
                }
                break;
            }
        }
        return dbs;
    }
}
