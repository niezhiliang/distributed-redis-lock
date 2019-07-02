package com.niezhiliang.redis.local.controller;

import com.niezhiliang.redis.local.utils.LockUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/07/02 10:36
 */
@RestController
public class IndexController {

    @Autowired
    private LockUtils lockUtils;


    @RequestMapping(value = "test")
    public String test() {
        String key = "hello";
        String value = "hellol";

        if (lockUtils.lock(key,value)) {
            System.out.println("获取锁成功");

            if (new Random().nextBoolean()) {
                lockUtils.unlock(key,value);
                System.out.println("解锁成功");
            }
        } else {
            System.out.println("获取锁失败");
        }
        return "success";
    }

}
