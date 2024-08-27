package com.situ.ws.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RedisIncrUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final long time = 1724744363728L;

    public static void main(String[] args) {
        // 获取当前时间
        long time1 = System.currentTimeMillis() - time;
        // 当前年月
//        Loc
//        redisTemplate.opsForValue().increment()
        System.out.println(time1);
        System.out.println(time1 << 32 | 1);
    }
}

