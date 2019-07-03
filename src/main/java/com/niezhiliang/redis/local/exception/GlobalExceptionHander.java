package com.niezhiliang.redis.local.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/07/03 08:54
 */
@ControllerAdvice
public class GlobalExceptionHander {

    /**
     * 处理所有的系统异常
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object SystemErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        Map<String,String> map = new HashMap<>();

        map.put("msg",e.getMessage());
        map.put("url",req.getRequestURL().toString());
        return map;
    }
}
