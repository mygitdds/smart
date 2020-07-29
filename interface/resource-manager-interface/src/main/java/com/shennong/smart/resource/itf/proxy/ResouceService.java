package com.shennong.smart.resource.itf.proxy;

import com.shennong.smart.resource.itf.proto.*;

import java.text.ParseException;

/**
 * ResouceService
 *资源管理服务service
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
public interface ResouceService {

    void createUpdateResouce(Resource resouce, String requestId);

    String createBatchCode(BatchCode batchCode, String requestId);

    Boolean createChCode(BatchCode batchCode, String requestId);

    GrantCodeRecordRsp grantCodeRecordApi(GrantCodeRecordReq grantCodeRecordReq);

    SelectResourceRsp selectResouceList(SelectResourceReq selectResourceReq);

    SelectCodeRsp selectCodeList(SelectCodeReq selectCodeReq);

    //核销卷码
    void verifyCode(SmartGrantCodeRecord smartGrantCodeRecord);

    //核销列表

    //
    void invalidCode() throws InterruptedException, ParseException;

}
