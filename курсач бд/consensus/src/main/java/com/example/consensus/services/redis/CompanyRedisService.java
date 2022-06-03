package com.example.consensus.services.redis;


import com.example.consensus.entities.redis.CompanyRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class CompanyRedisService {
    private final String EMPLOYEE_CACHE = "COMPANY";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, CompanyRedis> hashOperations;

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(final CompanyRedis employee) {
        hashOperations.put(EMPLOYEE_CACHE, employee.getId(), employee);
    }

    public CompanyRedis findById(final String id) {
        return (CompanyRedis) hashOperations.get(EMPLOYEE_CACHE, id);
    }

    public Map<String, CompanyRedis> findAll() {
        return hashOperations.entries(EMPLOYEE_CACHE);
    }

    public void delete(String id) {
        hashOperations.delete(EMPLOYEE_CACHE, id);
    }
}
