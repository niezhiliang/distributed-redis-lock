package com.niezhiliang.redis.local;

import com.niezhiliang.redis.local.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/07/03 09:42
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 300; i++) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    log.info(Thread.currentThread().getName()+"---> {}",HttpUtils.get("http://127.0.0.1:8080/test"));
                }
            });
            thread.start();
            Thread.currentThread().sleep(10);
        }
    }

}
