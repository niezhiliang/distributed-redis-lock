package com.niezhiliang.redis.local.controller;

import com.niezhiliang.redis.local.aspect.RedisLock;
import com.niezhiliang.redis.local.utils.LockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.klock.annotation.Klock;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/07/02 10:36
 */
@RestController
@Slf4j
public class IndexController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LockUtils lockUtils;


    /**
     * 普通的方式
     * @return
     */
    @RequestMapping(value = "test")
    public String test() {
        String key = "hello";
        String value = UUID.randomUUID().toString();
        try {
            if (lockUtils.lock(key,value)) {
                log.info("获取锁成功");
            } else {
                log.error("获取锁失败");
            }
            return "success";
        } finally {
            lockUtils.unlock(key,value);
        }
    }

    /**
     * 注解的方式
     * @return
     */
    @RequestMapping(value = "test2")
    @RedisLock(lockKey = "helloAnno",lockTime = 5l,tryCount = 2,tryTime = 2)
    public String testAnno() {
        log.info(Thread.currentThread().getName()+"抢到锁，返回成功");
        return "success";
    }

    /**
     * 注解的方式
     * @return
     */
    @RequestMapping(value = "test3")
    @Klock(waitTime = Long.MAX_VALUE)
    public String testAnno2() {
        log.info(Thread.currentThread().getName()+"抢到锁，返回成功");
        return "success";
    }

}
