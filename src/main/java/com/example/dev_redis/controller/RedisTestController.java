package com.example.dev_redis.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

    private final StringRedisTemplate redisTemplate;

    public RedisTestController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/redis/set")
    public String setValue() {
        redisTemplate.opsForValue().set("hello", "world");
        return "set ok";
    }

    @GetMapping("/redis/get")
    public String getValue() {
        String value = redisTemplate.opsForValue().get("hello");
        return value != null ? value : "no value";
    }
}
