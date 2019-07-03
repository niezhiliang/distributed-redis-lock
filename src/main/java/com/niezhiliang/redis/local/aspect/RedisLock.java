package com.niezhiliang.redis.local.aspect;

import java.lang.annotation.*;
import java.util.Random;
import java.util.UUID;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/07/02 17:00
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    /**
     * 分布式锁key
     * @return
     */
    String lockKey();

    /**
     * 自动释放锁的时间 单位秒
     * @return
     */
    long lockTime() default 3l;


    /**
     * 没有获取到锁重试次数
     * @return
     */
    int tryCount() default 1;


    /**
     * 多少时间后重试秒
     * @return
     */
    int tryTime() default 1;

    /**
     * 分布式锁key的值
     * @return
     */
    String lockValue() default "";
}
