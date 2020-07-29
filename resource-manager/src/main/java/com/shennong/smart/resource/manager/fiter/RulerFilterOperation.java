package com.shennong.smart.resource.manager.fiter;

import com.alibaba.fastjson.JSONObject;
import com.shennong.smart.resource.itf.proto.Code;


import java.util.ArrayList;
import java.util.List;

/**
 * 注册规则的实现类
 */
public class RulerFilterOperation {

    public static RulerFilterOperation getInstance(){
        RulerFilterOperation rulerFilterOperation = new RulerFilterOperation();
        rulerFilterOperation.createRulerFilter();
        return rulerFilterOperation;
    }

    private List<RulerFilter> rulerFilters = new ArrayList<>();

    public void register(RulerFilter rulerFilter){
        rulerFilters.add(rulerFilter);
    }
    //执行filter链
    public void runRulerFilter(List<Code> code, JSONObject jsonObject, List<Code> codeInvalid){
        for(RulerFilter rulerFilter : rulerFilters){
            rulerFilter.runRuler(code,jsonObject,codeInvalid);
        }
    }

    public void createRulerFilter(){
        RulerFilterOverTime rulerFilterOverTime = new RulerFilterOverTime();
        register(rulerFilterOverTime);
    }
}
