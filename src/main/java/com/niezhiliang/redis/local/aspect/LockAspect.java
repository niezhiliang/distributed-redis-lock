package com.niezhiliang.redis.local.aspect;

import com.niezhiliang.redis.local.utils.LockUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/07/02 17:12
 */
@Component
@Slf4j
@Aspect
public class LockAspect {

    @Autowired
    private LockUtils lockUtils;


    @Around("@annotation(redisLock)")
    public Object redisLock (ProceedingJoinPoint point,RedisLock redisLock) throws Throwable {
        String lockKey = redisLock.lockKey();
        String lockValue = UUID.randomUUID().toString().replaceAll("-","");

        System.out.println(redisLock.lockValue());

        if (!lockUtils.lock(lockKey,lockValue)) {

            int tryCount = redisLock.tryCount();
            int sleepTime = redisLock.tryTime();
            int i = 1;
            do {
                tryCount --;
                Thread.sleep(sleepTime * 1000);
                if (lockUtils.lock(lockKey,lockValue)) {
                    log.info(Thread.currentThread().getName()+"已经获取到锁，这是第{}次重试",i);
                    break;
                }
                log.info(Thread.currentThread().getName()+"获取锁失败，这是第{}次尝试拿锁",i);
                i++;
                if (tryCount == 0) {
                    throw new Exception(Thread.currentThread().getName()+"获取锁失败");
                }
            } while (tryCount > 0);
        }
        return point.proceed();
    }

    @AfterReturning(value = "@annotation(redisLock)")
    public void afterReturning(JoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        //TODO 解锁暂时还没想好好的解锁方式，关键是保存的value传不过来
        System.out.println("sdfsdfsdfsdf");
    }
}
