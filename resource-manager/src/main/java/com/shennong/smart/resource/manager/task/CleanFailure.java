package com.shennong.smart.resource.manager.task;

import com.shennong.smart.resource.itf.proxy.ResouceService;
import com.shennong.smart.resource.manager.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
@Service
@Slf4j
public class CleanFailure {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ResouceService resouceService;

    public boolean lock(String key) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key,"lock", 6000L, TimeUnit.SECONDS);
        return success != null && success;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void task() {
        if(lock(Constant.REDIS_KEY)){
            try {
                resouceService.invalidCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
