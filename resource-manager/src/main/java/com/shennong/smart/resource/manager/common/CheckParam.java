package com.shennong.smart.resource.manager.common;

import org.springframework.util.StringUtils;

public class CheckParam {
    public static void check(String... a) throws Exception {
        for(int i=0;i<a.length;i++){
            //判断每个参数
            String data = a[i];
            if(StringUtils.isEmpty(data)){
                throw new Exception();
            }
        }

    }
}
