package com.example.consensus.controllers.redis;

import com.example.consensus.entities.redis.ForecastRedis;
import com.example.consensus.services.redis.ForecastRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/redis")
public class RedisController {
    @Autowired
    ForecastRedisService forecastRedisService;

    @PostMapping("/forecast")
    public String save(@RequestBody final ForecastRedis forecastRedis) {
        forecastRedisService.save(forecastRedis);
        return "Successfully added. Forecast with id= " + forecastRedis.getId();
    }

    @PostMapping("/forecast/thousand")
    public String saveThousand(@RequestBody final ForecastRedis forecastRedis) {
        forecastRedisService.saveThousand(forecastRedis);
        return "Successfully added. Forecast with id= " + forecastRedis.getId();
    }

    @GetMapping("/forecast/all/time")
    public String findAllTime() {
        Long start = System.currentTimeMillis();
        final Map<String, ForecastRedis> employeeMap = (Map<String, ForecastRedis>) forecastRedisService.findAll();
        Long stop = System.currentTimeMillis();
        return "Время запроса = " + (stop - start);
    }

    @GetMapping("/forecast/all")
    public Map<String, ForecastRedis> findAllForecasts() {
        final Map<String, ForecastRedis> employeeMap = (Map<String, ForecastRedis>) forecastRedisService.findAll();
        return employeeMap;
    }
}
