package com.shennong.smart.resource.manager.mapper;


import com.shennong.smart.resource.itf.proto.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Result;

import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface ResourceMapper {
    //新增主资源
    @Insert({
            "<script>",
            " insert into t_smart_resource ( enterprise_id, img,",
            "<if test = 'resource.resourceId != null'>",
            "resource_id,",
            "</if>",
            "<if test = 'resource.name != null'>",
            "resource_name,",
            "</if>",
            "type, stock_manager, operator,foreign_resource_id)",
            "values( #{resource.enterpriseId}, #{resource.img},",
            "<if test = 'resource.resourceId != null'>",
            "#{resource.resourceId},",
            "</if>",
            "<if test = 'resource.name != null'>",
            "#{resource.name},",
            "</if>",
            "#{resource.type}, #{resource.stockManager}, #{resource.operator},#{foreignResourceId})",
            "</script>",
    })
    int createResource(@Param("resource") Resource resource, @Param("foreignResourceId") String foreignResourceId);

    //修改主资源
    @Update({
            "<script>",
            "UPDATE t_smart_resource SET img =#{img},",
            "<if test = 'resourceId != null'>",
                "resource_id =#{resourceId},",
            "</if>",
            "`resource_name` = #{name} ,`type` = #{type}, stock_manager = #{stockManager},operator = #{operator}",
            "where id = #{id}",
            "</script>",
    })
    int updateResouce(Resource resouce);

    //新增批次
    @Insert({
            "insert into t_smart_batch_code(resource_id, verify_type,claim_rules, code_number, grant_type,brush_count, lock_time)",
            "values(#{resourceId}, #{verifyType},#{claimRulesString}, #{codeNumber}, #{grantType},#{brushCount}, #{lockTime})",
    })
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int createBatchCode(BatchCode batchCode);

    //根据id删除
    @Delete({"DELETE FROM t_uma_batch_code where id = #{id}"})
    int deleteBatchCodeById(Long id);


    //批量新增卷
    @Insert({
            "<script>",
            "insert into t_smart_code (batch_code_id, enterprise_id,code, code_status,resource_id,edition) values",
            "<foreach collection=\"codes\" separator=\",\" item=\"item\" index=\"i\">",
            "(#{batchCodeId},#{enterpriseId},#{item},#{codeStatus},#{resourceId},#{edition})",
            "</foreach>",
            "</script>",

    })
    void createCode(CreateCodeBatch data);

    //做失效的操作
    @Update({
            "<script>",
            "UPDATE t_smart_code SET code_status = '3' where enterprise_id = #{enterpriseId} and id in (" +
            "<foreach collection=\"code\" separator=\",\" item=\"item\" index=\"i\">",
            "#{item.id}",
            "</foreach>",
            ")",
            "</script>",
    })
    void invalidCode(@Param("code") List<Code> code, @Param("enterpriseId") Long enterpriseId);

    //删除关于批次
    @Delete({"DELETE FROM t_smart_code where batch_code_id = #{batchCodeId}"})
    void deleteCode(Long batchCodeId);

    //新增发卷记录-- verify_time, resource_name, code,examine_name, examine_phone, cashPrize_phone
    @Insert({
            "insert into t_smart_grant_code_record ( code_id, winning_number, prize_id, open_id, phoneNumber,prize_type, activity_id, business_party,reason",
            ") values",
            "( #{codeId}, #{winningNumber},#{prizeId}, #{openId}, #{phoneNumber},#{prizeType}, #{activityId}, #{businessParty},#{reason})",
    })
    int createGrantCodeRecord(GrantCodeRecordReq grantCodeRecordReq);
    //根据资源id去拿到卷
    @Select({
            "select id,batch_code_id,enterprise_id,code,code_status,verify_time,edition,insert_time,update_time from t_smart_code where enterprise_id = #{enterpriseId} and resource_id = #{resourceId}  and code_status = 0",
            "order by id limit #{page},#{rows}"
    })
    @Results(id = "code", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "batchCodeId", column = "batch_code_id"),
            @Result(property = "enterpriseId", column = "enterprise_id"),
            @Result(property = "code", column = "code"),
            @Result(property = "codeStatus", column = "code_status"),
            @Result(property = "verifyTime", column = "verify_time"),
            @Result(property = "edition", column = "edition"),
            @Result(property = "writeTime", column = "write_time"),
            @Result(property = "insertTime", column = "insert_time"),
            @Result(property = "updateTime", column = "update_time"),
    })
    List<Code> getCodeByresouceId(Long resourceId, int page, int rows, Long enterpriseId);

    @Select({
            "select id,batch_code_id,enterprise_id,code,code_status,verify_time,edition,insert_time,update_time from t_smart_code where code_status = '1'",
    })
    @ResultMap("code")
    List<Code> getAllBatchCode();

    @Select({
            "select id,batch_code_id,enterprise_id,code,code_status,verify_time,edition,insert_time,update_time from t_smart_code where enterprise_id = #{enterpriseId}",
            "and executor = #{requestId}",
    })
    @ResultMap("code")
    List<Code> getCodeByRequestId(Long enterpriseId, String requestId);

    @Select({
            "select batch_code_id from t_smart_code enterprise_id = #{enterpriseId}",
            "and id = #{id}",
    })

    Long getCodeById(Long enterpriseId, Long id);


    //修改code根据版本号，做修改
    @Update({"UPDATE t_smart_code SET code_status = #{codeStatus},executor =#{executor}, edition = edition + 1 where enterprise_id = #{enterpriseId} and id = #{id} and  edition = #{edition}"})
    int updateCodeGrant(Code code);

    @Update({"UPDATE t_smart_code SET code_status = #{codeStatus} where enterprise_id = #{enterpriseId} and id = #{id}"})
    int verifyCode(Code code);

    //查询主资源
    @Select({
            "<script>",
            "select id,enterprise_id,img,resource_id,resource_name,type,stock_manager,operator,insert_time,update_time,grant_num,invalid_num,verify_num,total",
            "from t_smart_resource where enterprise_id =#{enterpriseId} ",
                "<if test = 'resourceId != null'>",
                "and  resource_id LIKE CONCAT(CONCAT('%', #{resourceId}), '%') ",
                "</if>",
                "<if test = 'resourceName != null'>",
                "and  resource_name LIKE CONCAT(CONCAT('%', #{resourceName}), '%') ",
                "</if>",
            "order by insert_time desc limit #{page},#{rows} ",
            "</script>",

    })
    //封装成对象传进来
    @Results(id = "resource", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "enterpriseId", column = "enterprise_id"),
            @Result(property = "img", column = "img"),
            @Result(property = "resourceId", column = "resource_id" ),
            @Result(property = "name", column = "resource_name"),
            @Result(property = "type", column = "type"),
            @Result(property = "stockManager", column = "stock_manager"),
            @Result(property = "operator", column = "operator"),
            @Result(property = "grantNum", column = "grant_num"),
            @Result(property = "invalidNum", column = "invalid_num"),
            @Result(property = "verifyNum", column = "verify_num"),
            @Result(property = "total", column = "total"),
            @Result(property = "insertTime", column = "insert_time"),

    })
    List<Resource> getResouce(SelectResourceReq selectResourceReq);
    @Select({
            "<script>",
                "select COUNT(id)",
                "from t_smart_resource where enterprise_id =#{enterpriseId} ",
                "<if test = 'resourceId != null'>",
                "and  resource_id LIKE CONCAT(CONCAT('%', #{resourceId}), '%') ",
                "</if>",
                "<if test = 'resourceName != null'>",
                "and  resource_name LIKE CONCAT(CONCAT('%', #{resourceName}), '%') ",
                "</if>",
                "order by insert_time desc",
            "</script>",
    })
    int getResouceSum(SelectResourceReq selectResourceReq);

    //根据id查询主资源
    @Select({
            "select id,enterprise_id,img,resource_id,resource_name,type,stock_manager,operator",
            "from t_smart_resource where id =#{id}"
    })
    @ResultMap("resource")
    Resource getResourceById(Long id);
    //根据资源id查询主资源
    @Select({
            "select id,enterprise_id,img,resource_id,resource_name,type,stock_manager,operator",
            "from t_smart_resource where foreign_resource_id =#{resourceId}"
    })
    @ResultMap("resource")
    List<Resource> getByResourceId(String resourceId);

    //查询批次
    @Select({
            "<script>",
            "select id,resource_id,verify_type,claim_rules,code_number,grant_type,brush_count,lock_time,grant_num,invalid_num,verify_num",
            "from t_smart_batch_code where resource_id = #{resourceId} order by insert_time desc limit #{page},#{rows}",
            "</script>",
    })
    //封装成对象传进来
    @Results(id = "batchCode", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "resourceId", column = "resource_id"),
            @Result(property = "verifyType", column = "verify_type"),
            @Result(property = "claimRulesString", column = "claim_rules"),
            @Result(property = "codeNumber", column = "code_number"),
            @Result(property = "grantType", column = "grant_type"),
            @Result(property = "brushCount", column = "brush_count"),
            @Result(property = "lockTime", column = "lock_time"),
            @Result(property = "grantNum", column = "grant_num"),
            @Result(property = "invalidNum", column = "invalid_num"),
            @Result(property = "verifyNum", column = "verify_num"),
    })
    List<BatchCode> getBatchCodeList(SelectCodeReq selectCodeReq);

    //查询批次出所有的批次
    @Select({
            "select id,resource_id,verify_type,claim_rules,code_number,grant_type,brush_count,lock_time,grant_num,invalid_num,verify_num",
            "from t_smart_batch_code "
    })
    @ResultMap("batchCode")
    List<BatchCode> allBatchCode();



    //获取总数量
    @Select({
            "<script>",
            "select COUNT(id)",
            "from t_smart_batch_code where resource_id = #{resourceId}",
            "</script>",
    })
    int getBatchCodeListSum(SelectCodeReq selectCodeReq);

    //增加主资源的卷数据
    @Update({
            "<script>",
            "update t_smart_resource SET ",
            "<if test = 'total != null'>",
            "total = total + #{total}",
            "</if>",
            "<if test = 'grantNum != null'>",
            "grant_num = grant_num + #{grantNum}",
            "</if>",
            "<if test = 'invalidNum != null'>",
            "invalid_num = invalid_num + #{invalidNum}",
            "</if>",
            "<if test = 'verifyNum != null'>",
            "verify_num = verify_num + #{verifyNum}",
            "</if>",
            "where id = #{resourceId}",
            "</script>",
    })
    int updateResourceData(UpdateResourceCode updateResourceCode);

    @Update({
            "<script>",
            "update t_smart_batch_code SET ",
            "<if test = 'grantNum != null'>",
            "grant_num = grant_num + #{grantNum}",
            "</if>",
            "<if test = 'invalidNum != null'>",
            "invalid_num = invalid_num + #{invalidNum}",
            "</if>",
            "<if test = 'verifyNum != null'>",
            "verify_num = verify_num + #{verifyNum}",
            "</if>",
            "where id = #{batchCodeId}",
            "</script>",
    })
    int updateBatchCode(UpdateBatchCode updateBatchCode);
    @Insert({
            "<script>",
            "insert into t_smart_grant_code_record (id, code_id, winning_number,",
            "prize_id, open_id, phoneNumber,",
            "prize_type, activity_id, business_party,",
            "reason, verify_time, resource_name,",
            "code, examine_name, examine_phone,",
            "cashPrize_phone)",
            "values (#{id,jdbcType=BIGINT}, #{codeId,jdbcType=BIGINT}, #{winningNumber,jdbcType=VARCHAR},",
            "#{prizeId,jdbcType=VARCHAR}, #{openId,jdbcType=VARCHAR}, #{phonenumber,jdbcType=VARCHAR},",
            "#{prizeType,jdbcType=VARCHAR}, #{activityId,jdbcType=VARCHAR}, #{businessParty,jdbcType=VARCHAR},",
            "#{reason,jdbcType=VARCHAR}, #{verifyTime,jdbcType=TIMESTAMP}, #{resourceName,jdbcType=VARCHAR},",
            "#{code,jdbcType=VARCHAR}, #{examineName,jdbcType=VARCHAR}, #{examinePhone,jdbcType=VARCHAR},",
            "#{cashprizePhone,jdbcType=VARCHAR})",
            "</script>",
    })
    int verifyCode(SmartGrantCodeRecord smartGrantCodeRecord);
}