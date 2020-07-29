package com.shennong.smart.common.ResultSet;

import lombok.Data;

/**
 * RespSmartData
 * 类作用：用户返回rpc数据集
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
@Data
public class RespSmartData<T> {
    private int code = 0;
    private T data;
    private String msg = "";

    public static RespSmartData success(){
        RespSmartData respSmartData = new RespSmartData();
        respSmartData.setCode(1001);
        respSmartData.setMsg("操作成功");
        return respSmartData;
    }

    public static RespSmartData fail(){
        RespSmartData respSmartData = new RespSmartData();
        respSmartData.setCode(1002);
        respSmartData.setMsg("操作失败");
        return respSmartData;
    }

    public RespSmartData success(T data){
        RespSmartData respSmartData = new RespSmartData();
        respSmartData.setCode(1001);
        respSmartData.setMsg("操作成功");
        respSmartData.setData(data);
        return null;
    }



}
