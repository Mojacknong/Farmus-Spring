package com.modernfarmer.farmusspring.domain.auth.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class RedisManager {
    private final RedisTemplate<String, String> redisTemplate;

    public void deleteValueByKey(String key) {
        redisTemplate.delete(key);
    }

    public void setValueByKey(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValueByKey(Long key){ return redisTemplate.opsForValue().get(String.valueOf(key));}
}
