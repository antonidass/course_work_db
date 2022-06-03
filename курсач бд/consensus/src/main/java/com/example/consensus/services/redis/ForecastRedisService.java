package com.example.consensus.services.redis;

import com.example.consensus.entities.Forecast;
import com.example.consensus.entities.redis.CompanyRedis;
import com.example.consensus.entities.redis.ForecastRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class ForecastRedisService {
    private final String FORECAST_CACHE = "FORECAST";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ForecastRedis> hashOperations;

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(final ForecastRedis forecastRedis) {
        hashOperations.put(FORECAST_CACHE, forecastRedis.getId(), forecastRedis);
    }

    public void saveThousand(final ForecastRedis forecastRedis) {
        for (int i = 0; i < 1000; i++) {
            hashOperations.put(FORECAST_CACHE, forecastRedis.getId() + i, forecastRedis);
        }
    }

    public ForecastRedis findById(final String id) {
        return (ForecastRedis) hashOperations.get(FORECAST_CACHE, id);
    }

    public Map<String, ForecastRedis> findAll() {
        return hashOperations.entries(FORECAST_CACHE);
    }

    public void delete(String id) {
        hashOperations.delete(FORECAST_CACHE, id);
    }
}
