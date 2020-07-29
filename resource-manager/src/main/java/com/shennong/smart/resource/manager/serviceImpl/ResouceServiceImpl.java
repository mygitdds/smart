package com.shennong.smart.resource.manager.serviceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shennong.smart.resource.itf.proto.*;
import com.shennong.smart.resource.itf.proxy.ResouceService;
import com.shennong.smart.resource.manager.common.AsynCount;
import com.shennong.smart.resource.manager.common.AsynCreateCode;
import com.shennong.smart.resource.manager.entry.CodeCount;
import com.shennong.smart.resource.manager.entry.CreateCodeStatus;
import com.shennong.smart.resource.manager.fiter.RulerFilterOperation;
import com.shennong.smart.resource.manager.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ResouceServiceImpl
 * 类作用：资源管理实现类
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
@Slf4j
public class ResouceServiceImpl implements ResouceService {

    @Autowired
    private ResourceMapper resouceMapper;

    @Autowired
    private AsynCreateCode synCreateCode;
    @Autowired
    private AsynCount asynCount;

    //新增修改主资源
    public void createUpdateResouce(Resource resouce, String requestId){
        log.info("{} [addResource] recv req:{}", requestId, resouce);
        Resource resouceNew = resouce;
        //先判断该对象是否有id
        if (resouceNew.getId() != null && resouceNew.getId() != 0) {

            resouceMapper.updateResouce(resouceNew);

        } else {
            //同步给外部资源得id
            String foreignResourceId = UUID.randomUUID().toString();
            resouceMapper.createResource(resouceNew, foreignResourceId);
        }
    }


    //新增批次
    public String createBatchCode(BatchCode batchCode, String requestId)  {
        log.info("{} [createBatchCode] recv req:{}", requestId, batchCode);
        checkNum(batchCode.getClaimRules(),requestId);
        //新增批次的同时，去新增卷码
        if (batchCode.getClaimRules() != null) {
            batchCode.setClaimRulesString(batchCode.getClaimRules().toJSONString());
        }
        resouceMapper.createBatchCode(batchCode);
        Boolean status = createChCode(batchCode, requestId);
        if (!status) {
            //回滚批次代码
            resouceMapper.deleteBatchCodeById(batchCode.getId());
            return "FAILED";
        }
        return "SUCCESS";
    }

    private void checkNum(JSONObject jsonObject, String requestId){
        if(jsonObject !=null){
            //limitedDayStart
            Integer limitedDayStart  = Integer.parseInt((String) jsonObject.get("limitedDayStart"));
            log.info("{} [checkNum] recv req:{}", requestId, limitedDayStart);
        }
    }

    //批量新增卷码
    public Boolean createChCode(BatchCode batchCode, String requestId)  {
        //记录新增装的状态
        CreateCodeStatus createCodeStatus = new CreateCodeStatus(true);
        List<String> listCode = createCode(batchCode);

        if (batchCode.getCodeNumber() < 1000) {
            createCodeOne(listCode, batchCode, requestId, createCodeStatus);
            if (!createCodeStatus.getStatus()) {
                //回滚一切代码
                resouceMapper.deleteCode(batchCode.getId());
            }else {
                updateResouceCode("total", batchCode.getCodeNumber(), batchCode.getResourceId());
            }
            return createCodeStatus.getStatus();
        } else {
            List<List<String>> listCodes = splitList1(listCode, 1000);
            List<List<List<String>>> codes = splitList1(listCodes, 10);
            createCodeStatus.setCount(codes.size());
            for (List<List<String>> data : codes) {
                synCreateCode.createCode(data, batchCode, requestId, createCodeStatus);
                log.info("{} [createChCodeStart] recv req:{}", requestId, "start");
            }
            return true;
        }

    }



    //一万个卷码一个线程来新增 status标注状态，主线程是否需要回滚
    public void createCodeOne(List<String> data, BatchCode batchCode, String requestId, CreateCodeStatus createCodeStatus) {
        log.info("{} [createCodeOne] recv req:{}", requestId, batchCode);
        CreateCodeBatch createCodeBatch = new CreateCodeBatch();
        createCodeBatch.setCodes(data);
        createCodeBatch.setEnterpriseId(batchCode.getEnterpriseId());
        createCodeBatch.setResourceId(batchCode.getResourceId());
        createCodeBatch.setBatchCodeId(batchCode.getId());
        createCodeBatch.setCodeStatus("0");
        createCodeBatch.setEdition(1);
        try {
            resouceMapper.createCode(createCodeBatch);
        } catch (Exception e) {
            log.error("{} [createCodeOne] recv req:{}", requestId, JSONObject.toJSONString(createCodeBatch));
            createCodeStatus.setStatus(false);
        }
    }

    //将list划分成多少等分
    public static <T> List<List<T>> splitList1(List<T> list, int pageSize) {
        List<List<T>> listArray = new ArrayList<List<T>>();
        List<T> subList = null;
        for (int i = 0; i < list.size(); i++) {
            if (i % pageSize == 0) {
                subList = new ArrayList<T>();
                listArray.add(subList);
            }
            subList.add(list.get(i));
        }
        return listArray;
    }
    //回滚提交



    //获取卷码
    private List<String> createCode(BatchCode batchCode) {
        int codeNumber = batchCode.getCodeNumber();
        List<String> listCode = new ArrayList<>(codeNumber);
        for (int i = 0; i < codeNumber; i++) {
            //获取6位数卷码
            listCode.add(UUID.randomUUID().toString());
        }
        return listCode;
    }

    //发卷-API方式
    public GrantCodeRecordRsp grantCodeRecordApi(GrantCodeRecordReq grantCodeRecordReq) {
        log.info("{} [createBatchCode] recv req:{}", grantCodeRecordReq.getRequestId(), grantCodeRecordReq);
        GrantCodeRecordRsp data = new GrantCodeRecordRsp();
        //查询到，该资源id
        List<Resource> resouceList = resouceMapper.getByResourceId(grantCodeRecordReq.getPrizeId());
        Resource resouce = null;
        Code code = null;
        if (!CollectionUtils.isEmpty(resouceList)) {
            resouce = resouceList.subList(0, 1).get(0);
            //拿到批次--根据insertTime的顺序拿出来，看谁有卷，
            CodeCount codeCount = new CodeCount(1);
            List<Code> dataCode = resouceMapper.getCodeByresouceId(resouce.getId(), 0, 100, resouce.getEnterpriseId());
            if (!CollectionUtils.isEmpty(dataCode)) {
                code = getCodeByApi(resouce, grantCodeRecordReq.getRequestId(), codeCount);
                if (!StringUtils.isEmpty(code)) {
                    //新增发卷记录
                    grantCodeRecordReq.setCodeId(code.getId());
                    grantCodeRecord(grantCodeRecordReq);
                    //修改主数据，--做成异步
                    asynCount.updateResouceCode("grantNum", 1, resouce.getId());
                    //修改批次表里面的数值--做成异步
                    asynCount.updateBatchCode("grantNum", 1, code.getBatchCodeId());
                }
            }
        }
        if(code == null){
            code = new Code();
        }
        data.setWinningNumber(grantCodeRecordReq.getWinningNumber());
        data.setCode(code.getCode());
        return data;
    }

    //新增发卷记录
    private void grantCodeRecord(GrantCodeRecordReq grantCodeRecordReq) {
        //去新增发卷记录
        resouceMapper.createGrantCodeRecord(grantCodeRecordReq);
    }

    private Code getCodeByApi(Resource resouce, String requestId, CodeCount codeCount) {
        List<Code> dataCode = resouceMapper.getCodeByresouceId(resouce.getId(), 0, 100, resouce.getEnterpriseId());
        Code code = null;
        if (CollectionUtils.isEmpty(dataCode)) {
            code = new Code();
        } else {
            Random random = new Random();
            int n = random.nextInt(dataCode.size());
            code = dataCode.get(n);
            code.setExecutor(requestId);
            code.setCodeStatus("1");
            resouceMapper.updateCodeGrant(code);
            //根据操作者id去查询一次，如果能拿到数据，说明是获取到了卷
            List<Code> check = resouceMapper.getCodeByRequestId(resouce.getEnterpriseId(), requestId);
            if (CollectionUtils.isEmpty(check)) {
                //当拿三次还没拿到，就退出了，说明拿不到。
                if (codeCount.getCodeCountNum() > 4) {
                    return new Code();
                }
                codeCount.addOne();
                //空得说明是被抢先了
                code.setCode(getCodeByApi(resouce, requestId, codeCount).getCode());
            }
        }
        return code;
    }

    //根据企业id查询资源列表
    public SelectResourceRsp selectResouceList(SelectResourceReq selectResourceReq) {
        SelectResourceRsp data = new SelectResourceRsp();
        //根据，企业id去拿到，资源id去拿到卷码表
        //处理数据
        String resourceName= selectResourceReq.getResourceName();
        String resourceId= selectResourceReq.getResourceId();
        //处理查询所有的模糊查询
        if(resourceName !=null){
            if(resourceName.contains("%") || resourceName.contains("_")){
                return data;
            }
        }else if(resourceId !=null) {

            if(resourceId.contains("%") || resourceId.contains("_")){
                return data;
            }
        }
        selectResourceReq.setPage((selectResourceReq.getPage() - 1) * selectResourceReq.getRows());
        //checkSelectResourceReq(selectResourceReq);
        List<Resource> listResource = resouceMapper.getResouce(selectResourceReq);

        data.setCodeList(listResource);
        int sum = resouceMapper.getResouceSum(selectResourceReq);
        data.setCodeSum(sum);
        return data;
    }

    //根据主资源信息查询批次表
    public SelectCodeRsp selectCodeList(SelectCodeReq selectCodeReq) {
        SelectCodeRsp selectCodeRsp = new SelectCodeRsp();
        int page = selectCodeReq.getPage() - 1;
        if (page < 0) {
            selectCodeReq.setPage(0);
        } else {
            selectCodeReq.setPage(page * selectCodeReq.getRows());
        }
        List<BatchCode> list = resouceMapper.getBatchCodeList(selectCodeReq);
        log.info("[selectCodeList] req:{} rsq:{}", selectCodeReq.toString(), list);

        int sum = resouceMapper.getBatchCodeListSum(selectCodeReq);
        selectCodeRsp.setCodeList(list);
        selectCodeRsp.setCodeSum(sum);
        return selectCodeRsp;
    }
    @Transactional
    @Override
    public void verifyCode(SmartGrantCodeRecord smartGrantCodeRecord) {
        resouceMapper.verifyCode(smartGrantCodeRecord);
        Code code = new Code();
        code.setEnterpriseId(smartGrantCodeRecord.getEnterpriseId());
        code.setId(smartGrantCodeRecord.getCodeId());
        resouceMapper.verifyCode(code);
        Long batchCodeId = resouceMapper.getCodeById(smartGrantCodeRecord.getEnterpriseId(),smartGrantCodeRecord.getCodeId());
        updateResouceCode("verifyNum",1,Long.valueOf(smartGrantCodeRecord.getPrizeId()));
        updateBatchCode("verifyNum",1,batchCodeId);
        //修改卷码状态

    }

    //新增主资源的票数据的加减
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
    //查询所有的批次
    public List<BatchCode> getAllBatchCode(){
        return resouceMapper.allBatchCode();
    }
    //查询出所有发放后，没有领取的卷码，要做失效操作
    public List<Code> getAllGrantCode(){

        return resouceMapper.getAllBatchCode();
    }
    //失效对应的code
    @Transactional
    public void invalidCodeOperation(List<Code> code,List<BatchCode> batchCodeList){
        log.info("[invalidCode] ", code.toString());
        Map<Long,List<Code>> group =code.stream().collect(Collectors.groupingBy(Code ::getEnterpriseId));
        Set<Long> enterpriseIdSet = group.keySet();
        for(Long data :enterpriseIdSet){
            List<Code> codes = group.get(data);
            resouceMapper.invalidCode(codes,data);
        }
        //修改批次
        Map<Long,List<Code>> batchCode = code.stream().collect(Collectors.groupingBy(Code ::getBatchCodeId));
        Set<Long> batchCodeSet = batchCode.keySet();
        //记录批次的数量
        Map<Long,Integer> batchCodeNum = new HashMap<>();
        for(Long data : batchCodeSet){
            List<Code> listCode = batchCode.get(data);
            int sum = listCode.size();
            updateBatchCode("invalidNum",sum,data);
            batchCodeNum.put(data,sum);
        }
        //记录资源失效数据
        Map<Long,List<BatchCode>> groupBatchCode = batchCodeList.stream().collect(Collectors.groupingBy(BatchCode ::getResourceId));
        Set<Long> groupBatchCodeObject = groupBatchCode.keySet();
        for (Long data : groupBatchCodeObject){
            List<BatchCode> listBatchCode = groupBatchCode.get(data);
            int Statistics = 0;
            for (BatchCode batchCodeData: listBatchCode){
                Integer sum = batchCodeNum.get(batchCodeData.getId());
                if(sum !=null && sum !=0){
                    Statistics = Statistics + sum;
                }
            }
            updateResouceCode("invalidNum",Statistics,data);
        }
    }

    //定时任务执行点
    public void invalidCode() throws InterruptedException, ParseException {
        //责任链操作类
        RulerFilterOperation rulerFilterOperation = RulerFilterOperation.getInstance();
        //查询所有的批次表数据
        List<BatchCode> batchCodeList = getAllBatchCode();
        List<Code> codeList = getAllGrantCode();
        //存放需要取失效的code
        List<Code> codeInvalid = new ArrayList<>();
        if (!CollectionUtils.isEmpty(batchCodeList)){
            if (!CollectionUtils.isEmpty(codeList)){
                Map<Long,List<Code>> group = codeList.stream().collect(Collectors.groupingBy(Code ::getBatchCodeId));
                for (BatchCode batchCode :batchCodeList){
                    //拿到批次对应的code
                    List<Code> code = group.get(batchCode.getId());
                    //把规则转换成json方便取到json
                    log.info("{} [task] recv req:{}",batchCode.getClaimRulesString());
                    JSONObject jsonObject = JSON.parseObject(batchCode.getClaimRulesString());
                    //执行责任链模式
                    rulerFilterOperation.runRulerFilter(code,jsonObject,codeInvalid);
                }
            }
        }
        invalidCode(codeInvalid,batchCodeList);
    }

    //失效卷码与修改主资源批次的失效数量
    private void invalidCode(List<Code> code,List<BatchCode> batchCodeList){
        log.info("{} [createBatchCode]  req:{}", JSON.toJSONString(code));
        List<Code> codes = code;
        List<BatchCode> batchCodeLists = batchCodeList;
        if(!CollectionUtils.isEmpty(codes)){
            invalidCodeOperation(codes,batchCodeLists);
        }
    }
}
