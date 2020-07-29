package com.shennong.smart.resource.manager.fiter;


import com.alibaba.fastjson.JSONObject;
import com.shennong.smart.resource.itf.proto.Code;

import java.util.List;

/**
 * 规则filter链
 */
public interface RulerFilter {
    void runRuler(List<Code> code, JSONObject jsonObject, List<Code> codeInvalid);
}
