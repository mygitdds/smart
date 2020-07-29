package com.shennong.smart.resource.manager.common;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redisConfig
 * 类作用：
 *
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
@Component

public class redisConfig {

    @Bean
    public JedisPoolConfig redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(15);
        jedisPoolConfig.setMaxWaitMillis(10);
        return jedisPoolConfig;
    }

    @Bean
    public JedisConnectionFactory getJedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("112.124.13.67");
        jedisConnectionFactory.setPassword("aB.967426");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setTimeout(3000);
        jedisConnectionFactory.setPoolConfig(redisPoolFactory());
        return jedisConnectionFactory;
    }
    @Bean("redisTemplate")
    public RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        return redisTemplate;
    }
}
