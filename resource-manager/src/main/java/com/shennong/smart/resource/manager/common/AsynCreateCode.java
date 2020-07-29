package com.shennong.smart.resource.manager.common;
import com.alibaba.fastjson.JSONObject;
import com.shennong.smart.resource.itf.proto.BatchCode;
import com.shennong.smart.resource.itf.proto.CreateCodeBatch;
import com.shennong.smart.resource.itf.proto.UpdateResourceCode;
import com.shennong.smart.resource.manager.entry.CreateCodeStatus;
import com.shennong.smart.resource.manager.mapper.ResourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class AsynCreateCode {
    @Autowired
    private ResourceMapper resourceMapper;


    //两千个卷码一个线程来新增 status标注状态，主线程是否需要回滚
    @Async("taskExecutor")
    public void createCode(List<List<String>> listCodes, BatchCode batchCode, String requestId,CreateCodeStatus createCodeStatus) {
        CreateCodeStatus status = createCodeStatus;
        BatchCode batchCodeNew = batchCode;
        SimpleDateFormat startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("{} [asyn-code-startTime] recv req:{}", requestId, startDate.format(new Date()));
        try {
            for (List<String> data : listCodes) {
                CreateCodeBatch createCodeBatch = new CreateCodeBatch();
                createCodeBatch.setCodes(data);
                createCodeBatch.setEnterpriseId(batchCode.getEnterpriseId());
                createCodeBatch.setResourceId(batchCode.getResourceId());
                createCodeBatch.setBatchCodeId(batchCode.getId());
                createCodeBatch.setCodeStatus("0");
                createCodeBatch.setEdition(1);
                resourceMapper.createCode(createCodeBatch);
            }
        } catch (Exception e) {
            log.info("{} [createCodeAsyncError] recv req:{}", requestId, JSONObject.toJSONString(batchCode));
            status.setStatus(false);
        }finally {
            status.countNum();
            if(status.getCount() == 0){
                if (!status.getStatus()){
                    //调用整体回滚
                    rollBACK(batchCodeNew,requestId);
                }else {
                    //去修改主资源
                    updateResouceCode(batchCode.getCodeNumber(), batchCode.getResourceId());
                }
            }
        }
        SimpleDateFormat endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("{} [asyn-code-endTime] recv req:{}", requestId, endDate.format(new Date()));
    }

    //回滚批次卷码接口
    private void rollBACK(BatchCode batchCode,String requestId){
        log.info("{} [asyn-code-rollBACK] recv req:{}", requestId,JSONObject.toJSONString(batchCode));
        try {
            resourceMapper.deleteCode(batchCode.getId());
            resourceMapper.deleteBatchCodeById(batchCode.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //新增主资源的票数据的加减
    public void updateResouceCode(Integer num, Long resourceId) {
        UpdateResourceCode updateResourceCode = new UpdateResourceCode();
        updateResourceCode.setResourceId(resourceId);
        updateResourceCode.setTotal(num);
        resourceMapper.updateResourceData(updateResourceCode);
    }

}
