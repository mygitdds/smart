package com.shennong.smart.resource.manager.common;
import com.shennong.smart.resource.itf.proto.UpdateBatchCode;
import com.shennong.smart.resource.itf.proto.UpdateResourceCode;
import com.shennong.smart.resource.manager.mapper.ResourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class AsynCount {
    @Autowired
    private ResourceMapper resouceMapper;

    //新增主资源的票数据的加减
    @Async("taskExecutor")
    public void updateResouceCode(String operationType, Integer num, Long resourceId) {
        UpdateResourceCode updateResourceCode = new UpdateResourceCode();
        updateResourceCode.setResourceId(resourceId);
        switch (operationType) {

            case "total":
                updateResourceCode.setTotal(num);
                break;
            case "grantNum":
                updateResourceCode.setGrantNum(num);
                break;
            case "invalidNum":
                updateResourceCode.setInvalidNum(num);
                break;
            case "verifyNum":
                updateResourceCode.setVerifyNum(num);
                break;
        }
        resouceMapper.updateResourceData(updateResourceCode);
    }

    //修改批次表的数值
    @Async("taskExecutor")
    public void updateBatchCode(String operationType, Integer num, Long BatchCodeId) {
        UpdateBatchCode updateBatchCode = new UpdateBatchCode();
        updateBatchCode.setBatchCodeId(BatchCodeId);
        switch (operationType) {
            case "grantNum":
                updateBatchCode.setGrantNum(num);
                break;
            case "invalidNum":
                updateBatchCode.setInvalidNum(num);
                break;
            case "verifyNum":
                updateBatchCode.setVerifyNum(num);
                break;
        }
        resouceMapper.updateBatchCode(updateBatchCode);
    }
}
