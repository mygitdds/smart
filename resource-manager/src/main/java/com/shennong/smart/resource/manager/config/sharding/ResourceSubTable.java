package com.shennong.smart.resource.manager.config.sharding;
import com.shennong.smart.common.base.StringUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * ResourceSubTable
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/9
 */
public class ResourceSubTable  implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {

        return selectLibraryName(collection,preciseShardingValue);
    }


    public String selectLibraryName(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        String tb = null;
        //通过循环匹配到相应的库--返回相应的库
        for (ResourceDbAndTableEnum targetEnum : ResourceDbAndTableEnum.values()) {
            if (targetEnum.getTableName().equals(shardingValue.getLogicTableName())) {
                //目标表的目标主键路由-例如：根据订单id查询订单信息
                if (targetEnum.getShardingKey().equals(shardingValue.getColumnName())) {
                    int db = shardingValue.getValue().intValue() % targetEnum.getTbCount();
                    for (String availableTargetName : availableTargetNames){
                        if (availableTargetName.endsWith("_"+db)) {
                            tb = availableTargetName;
                            break;
                        }
                    }
                }
                break;
            }
        }
        return tb;
    }
}
