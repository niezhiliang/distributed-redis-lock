package com.niezhiliang.redis.local.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/07/02 10:49
 */
@Component
public class LockUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DefaultRedisScript<Long> redisScript;

    private final Long RELEASE_SUCCESS = 1L;

    private final Long TIME_OUT = 5L;




    /**
     * 加锁
     * @param key
     * @return
     */
    public boolean lock(String key,String value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key,value, TIME_OUT, TimeUnit.SECONDS);
    }

    /**
     * 释放锁
     * @param key
     * @return
     */
    public boolean unlock(String key,String value) {
        //使用Lua脚本：先判断是否是自己设置的锁，再执行删除
        Long result = stringRedisTemplate.execute(redisScript, Arrays.asList(key,value));
        //返回最终结果
        return RELEASE_SUCCESS.equals(result);
    }


    @Bean
    public DefaultRedisScript<Long> defaultRedisScript() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptText("if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end");
        return defaultRedisScript;
    }


}
