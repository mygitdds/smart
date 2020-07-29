package com.shennong.smart.resource.manager.fiter;

import com.alibaba.fastjson.JSONObject;

import com.shennong.smart.resource.itf.proto.Code;
import com.shennong.smart.resource.manager.common.DateUtil;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Slf4j
public class RulerFilterOverTime implements RulerFilter  {
    @Override
    public void runRuler(List<Code> code, JSONObject jsonObject, List<Code> codeInvalid) {
        Integer limitedDayStart = Integer.parseInt((String) jsonObject.get("limitedDayStart"));
        log.info("规则的天数是"+limitedDayStart);
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.format(new Date());
        for(Code data : code){
            int difference = 0;
            try {
                difference = DateUtil.daysBetween(new Date(),data.getUpdateTime());
                log.info("相差的天数是"+difference);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(difference<limitedDayStart){
                codeInvalid.add(data);
            }
        }
    }
}
